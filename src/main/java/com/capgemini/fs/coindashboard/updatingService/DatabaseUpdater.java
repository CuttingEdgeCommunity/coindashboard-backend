package com.capgemini.fs.coindashboard.updatingService;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.UpdateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.utils.AsyncService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@Setter
public class DatabaseUpdater {

  private final int MAX_COINS = 250;
  private boolean enabled = true;
  @Autowired private AsyncService asyncService;
  @Autowired private UpdateQueries updateQueries;
  @Autowired private GetQueries getQueries;
  @Autowired private CreateQueries createQueries;
  @Autowired private ApiHolder apiHolder;

  private List[] comparerCurrentAndPreviousResult(
      Map<String, Integer> prev_coins_map, List<Coin> curr_coins) {
    log.info("Comparer launched...");
    long start = System.currentTimeMillis();
    int counterAlreadyFullyUpdated = 0; // TODO remove counter
    List<String> not_in_db = new ArrayList<>();
    List<Coin> marketCapRank_update = new ArrayList<>();
    for (Coin curr_coin : curr_coins) {
      String symbol = curr_coin.getSymbol();
      if (!getQueries.isCoinInDBBySymbol(symbol)) {
        not_in_db.add(symbol);
        marketCapRank_update.add(curr_coin);
      } else {
        if (prev_coins_map.containsKey(symbol)) {
          if (prev_coins_map.get(symbol).equals(curr_coin.getMarketCapRank())) {
            for (String currency : curr_coin.getQuotes().keySet()) {
              updateQueries.updateCoinCurrentQuote(
                  symbol, curr_coin.getQuotes().get(currency).getCurrentQuote(), currency);
            }
            counterAlreadyFullyUpdated++; // TODO remove counter
          } else {
            marketCapRank_update.add(curr_coin);
          }
          prev_coins_map.remove(symbol);
        } else {
          marketCapRank_update.add(curr_coin);
        }
      }
    }
    log.info(
        "{} coins didn't need marketCapRank update and has been successfully updated.",
        counterAlreadyFullyUpdated);
    log.info("{} coins wasn't in db.", not_in_db.size());
    log.info("{} coins need also marketCapRank update.", marketCapRank_update.size());
    log.info("{} coins has been kicked from top 250.", prev_coins_map.size());
    log.info(
        "Comparer successfully finished. Elapsed time was: {} ms.",
        System.currentTimeMillis() - start);
    return new List[] {not_in_db, marketCapRank_update, prev_coins_map.keySet().stream().toList()};
  }

  // Updating current market data for top 250 coins each few seconds.
  @Async
  @Scheduled(fixedDelay = 10000)
  public Boolean currentQuoteUpdates() {
    if (this.enabled) {
      try {
        List<String> vsCurrencies = List.of("usd");
        var resultTop =
            this.apiHolder.getTopCoins(
                List.of(ApiProviderEnum.COIN_GECKO), MAX_COINS, 0, vsCurrencies);
        List<Coin> curr_coins = resultTop.orElseThrow().getCoins();
        String prev_coins = getQueries.getCoinsSimple(MAX_COINS, 0);
        Map<String, Integer> prev_coins_map = jsonToMapForMarketCapRank(prev_coins);
        List[] data_lists = comparerCurrentAndPreviousResult(prev_coins_map, curr_coins);
        List<String> not_in_db = data_lists[0];
        List<Coin> marketCapRank_update = data_lists[1];
        List<String> kicked_from_top = data_lists[2];
        Optional<Result> resultInfoNotInDb;
        if (!not_in_db.isEmpty()) {
          resultInfoNotInDb =
              this.apiHolder.getCoinInfo(List.of(ApiProviderEnum.COIN_GECKO), not_in_db);
          List<Coin> coinsNotInDb = resultInfoNotInDb.orElseThrow().getCoins();
          createQueries.createCoinDocuments(coinsNotInDb);
        }
        updateQueries.updateTopCoinsTransaction(curr_coins, kicked_from_top, marketCapRank_update);
        return true;
      } catch (Exception ex) {
        log.error(ex);
        return false;
      }
    }
    return false;
  }

  @Async
  @Scheduled(cron = "* */5 * * * *")
  public Boolean chartUpdate() {
    if (this.enabled) {
      this.updateQueries.UpdateEveryCoinPriceChart();
      return true;
    }
    return false;
  }

  private Map<String, Integer> jsonToMapForMarketCapRank(String jsonString)
      throws JsonProcessingException {
    JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
    Map<String, Integer> map = new HashMap<>();
    for (JsonNode node :
        jsonNode) { // TODO Remove this if when we are sure there will be no duplicates in db.
      if (map.containsKey(node.get("symbol").asText())) {
        updateQueries.removeDuplicates(node.get("id").toString().replace("\"", ""));
      } else {
        map.put(node.get("symbol").asText(), node.get("marketCapRank").asInt());
      }
    }
    return map;
  }
}
