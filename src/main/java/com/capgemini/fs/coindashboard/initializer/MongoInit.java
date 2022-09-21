package com.capgemini.fs.coindashboard.initializer;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
// class for initialization of a mongoDB with historical data of coins
public class MongoInit implements InitializingBean {

  @Autowired private CreateQueries createQueries;
  @Autowired private GetQueries getQueries;
  @Autowired private ApiHolder apiHolder;
  @Autowired private MongoTemplate mongoTemplate;

  @Override
  public void afterPropertiesSet() {
    log.info("Starting initialization...");
    requestingInitialData();
  }

  // method to make code cleaner
  protected void requestingInitialData() {
    log.info("Requesting data...");
    final byte PAGES = 2;
    int TAKE = 500;
    List<String> symbols = new ArrayList<>();
    Optional<Result> coinMarketDataResult;
    for (byte i = 0; i < PAGES; i++) {
      try {
        coinMarketDataResult =
            Optional.of(apiHolder.getTopCoins(TAKE, i, List.of("usd"))).orElse(null);
        log.info("Requested 250 topCoins from {} page", i);
        try {
          var res = coinInfo(coinMarketDataResult);
          passingData(res);
        } catch (Exception e) {
          log.error(e.getMessage());
        }
      } catch (Exception e) {
        log.error("Problem with symbols: {}", e.getMessage());
      }
    }
  }

  // coin info wrapper
  protected List<Coin> coinInfo(Optional<Result> coinMarketDataResult) {
    List<Coin> coins = new ArrayList<>();
    var result =
        this.apiHolder.getCoinInfo(
            coinMarketDataResult.get().getCoins().stream()
                .map(Coin::getSymbol)
                .collect(Collectors.toList()));
    // maps
    Map<String, Coin> resultMap =
        result.get().getCoins().stream()
            .collect(
                Collectors.toMap(
                    Coin::getSymbol,
                    (c) -> c,
                    (coinlhs, coinrhs) -> {
                      log.info("duplicate symbol: {}", coinlhs.getSymbol());
                      return coinlhs;
                    }));
    for (Coin coin : coinMarketDataResult.get().getCoins()) {
      coins.add(new Coin(resultMap.get(coin.getSymbol()), coin));
    }
    log.info(coins.size());
    return coins;
  }
  // passer to queries
  protected void passingData(List<Coin> coins) {
    log.info("Passing data...");
    try {
      createQueries.CreateCoinDocuments(coins);
      log.info("Successfully added initial {} topCoins data", coins.size());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
