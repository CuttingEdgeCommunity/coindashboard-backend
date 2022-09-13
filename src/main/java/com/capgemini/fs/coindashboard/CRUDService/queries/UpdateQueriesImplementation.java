package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.mongodb.client.ClientSession;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UpdateQueriesImplementation implements UpdateQueries {
  @Autowired MongoTemplate mongoTemplate;
  @Autowired ClientSession mongoSession;
  @Autowired private CreateQueries createQueries;
  @Autowired private GetQueries getQueries;
  @Autowired private ApiHolder apiHolder;

  @Override
  public boolean updateCoinCurrentQuote(String symbol, CurrentQuote newQuote, String vs_currency) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("symbol").is(symbol));
      Update update = new Update();
      update.set("quotes." + vs_currency.toLowerCase() + ".currentQuote", newQuote);
      UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Coin.class);
      if (updateResult.wasAcknowledged()) {
        log.info("Update CurrentQuote for " + symbol + " vs " + vs_currency + " performed.");
        return true;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }
  // Updating current market data for coins which already in database and creating new documents for
  // new coins.
  public boolean updateCoinsCurrentQuotes(Result result) {
    List<Coin> coins = result.getCoins();
    for (Coin coin : coins) {
      if (!getQueries.isCoinInDBBySymbol(coin.getSymbol())) {
        try {
          List<String> symbols = List.of(coin.getSymbol());
          var resultCoinInfo = this.apiHolder.getCoinInfo(symbols);
          Coin coinWithDetails = resultCoinInfo.orElseThrow().getCoins().get(0);
          coin.setContract_address(coinWithDetails.getContract_address());
          coin.setDescription(coinWithDetails.getDescription());
          coin.setGenesis_date(coinWithDetails.getGenesis_date());
          coin.setImage_url(coinWithDetails.getImage_url());
          coin.setIs_token(coinWithDetails.getIs_token());
          coin.setLinks(coinWithDetails.getLinks());
          createQueries.createCoinDocument(coin);
          log.info("new coin (" + coin.getSymbol() + ") in the database.");
        } catch (Exception ex) {
          log.error(ex.getMessage());
        }
      } else {
        for (String currency : coin.getQuotes().keySet()) {
          try {
            updateCoinCurrentQuote(
                coin.getSymbol(), coin.getQuotes().get(currency).getCurrentQuote(), currency);
            log.info("Update of coin: " + coin.getSymbol() + " has started...");
          } catch (Exception ex) {
            log.error(ex.getMessage());
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean UpdateCoinPriceChart(String symbol) {

    return false;
  }

  @Override
  public boolean UpdateCoinMarketCapRank(String symbol, Integer marketCapRank) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("symbol").is(symbol));
      Update update = new Update();
      update.set("marketCapRank", marketCapRank);
      UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Coin.class);
      if (updateResult.wasAcknowledged()) {
        log.info("Update performed");
        return true;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }
  // Clearing the current marketCapRank and overwriting the retrieved values by Integer.MAX_VALUE.
  @Override
  public boolean cleanCoinsMarketCapRanks(int coinsAmount) {
    try {
      Query query = new Query(Criteria.where("rank").gte(1).lte(coinsAmount));
      Update update = new Update();
      update.set("marketCapRank", Integer.MAX_VALUE);
      UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Coin.class);
      if (updateResult.wasAcknowledged()) {
        log.info("MarketCapRank has been cleared for previous update.");
        return true;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  @Override
  public boolean UpdateEveryCoinPriceChart() {
    return false;
  }
  // Cleaning marketCapRank for coins which are out from current Top 250 and updating data for rest
  // coins from new api call.
  @Override
  public boolean updateTopCoinsTransaction(Result result) {
    List<Coin> coins = result.getCoins();
    mongoTemplate
        .withSession(mongoSession)
        .execute(
            action -> {
              mongoSession.startTransaction();
              try {
                cleanCoinsMarketCapRanks(coins.size());
                updateCoinsCurrentQuotes(result);
                mongoSession.commitTransaction();
              } catch (RuntimeException e) {
                mongoSession.abortTransaction();
                log.info("Error during updating current quotes... " + e.getMessage());
                return false;
              }
              return true;
            });
    return true;
  }
}
