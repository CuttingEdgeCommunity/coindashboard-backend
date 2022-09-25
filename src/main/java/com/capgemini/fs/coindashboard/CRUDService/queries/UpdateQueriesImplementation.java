package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Log4j2
public class UpdateQueriesImplementation implements UpdateQueries {
  @Autowired MongoTemplate mongoTemplate;
  // @Autowired ClientSession clientSession;
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
        log.debug("Update CurrentQuote for " + symbol + " vs " + vs_currency + " is completed.");
        return true;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  @Override
  public boolean updateCoinCurrentQuoteAndMarketCapRank(
      String symbol, CurrentQuote newQuote, String vs_currency, Integer marketCapRank) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("symbol").is(symbol));
      Update update = new Update();
      update
          .set("quotes." + vs_currency.toLowerCase() + ".currentQuote", newQuote)
          .set("marketCapRank", marketCapRank);
      UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Coin.class);
      if (updateResult.wasAcknowledged()) {
        log.debug(
            "Update CurrentQuote and marketCapRank for "
                + symbol
                + " vs "
                + vs_currency
                + " has been completed.");
        return true;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }
  // Updating current market data for coins which already in database.
  public boolean updateCoinsCurrentQuotesAndMarketCapRank(List<Coin> coins) {
    for (Coin coin : coins) {
      for (String currency : coin.getQuotes().keySet()) {
        try {
          updateCoinCurrentQuoteAndMarketCapRank(
              coin.getSymbol(),
              coin.getQuotes().get(currency).getCurrentQuote(),
              currency,
              coin.getMarketCapRank());
        } catch (Exception ex) {
          log.error(ex.getMessage());
        }
      }
    }
    log.info("{} coins has been successfully updated with marketCapRank.", coins.size());
    return true;
  }

  @Override
  public boolean updateCoinPriceChart(String symbol, String vs_currency, List<Price> chart) {
    try {
      Query query = new Query(Criteria.where("symbol").is(symbol));
      Update update = new Update();
      update.set("quotes." + vs_currency.toLowerCase() + ".chart", chart);
      UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Coin.class);
      if (updateResult.wasAcknowledged()) {
        log.debug(
            "Update CurrentQuote and marketCapRank for "
                + symbol
                + " vs "
                + vs_currency
                + " has been completed.");
        return true;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  // Clearing the current marketCapRank and overwriting the retrieved values by Integer.MAX_VALUE.
  @Override
  public boolean cleanCoinsMarketCapRanks(List<String> symbols) {
    try {
      Query query = new Query(Criteria.where("symbol").in(symbols));
      Update update = new Update();
      update.set("marketCapRank", Integer.MAX_VALUE);
      UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Coin.class);
      if (updateResult.wasAcknowledged()) {
        log.info(
            "MarketCapRank for {} coins from previous update has been cleared.", symbols.size());
        return true;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  @Override
  public boolean updateEveryCoinPriceChart() {
    return false;
  }
  // Cleaning marketCapRank for coins which are out from current Top 250 and updating data for rest
  // coins from new api call.
  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean updateTopCoinsTransaction(
      List<Coin> top_coins, List<String> kicked_from_top, List<Coin> marketCapRank_update) {
    log.info("Update transaction for {} remain coins has started.", marketCapRank_update.size());
    long start = System.currentTimeMillis();
    try {
      cleanCoinsMarketCapRanks(kicked_from_top);
      updateCoinsCurrentQuotesAndMarketCapRank(marketCapRank_update);
      log.info(
          "Update transaction has been successfully finished. Elapsed time was: {} ms.",
          System.currentTimeMillis() - start);
    } catch (RuntimeException e) {
      log.info("Error during updating current quotes... " + e.getMessage());
      return false;
    }
    return true;
  }

  public void removeDuplicates(String id) {
    Query query = new Query(Criteria.where("id").is(id));
    mongoTemplate.findAndRemove(query, Coin.class, "Coin");
  }

  @Override
  public boolean updateTopCoinsPriceChart(List<Coin> coins) {
    for (Coin coin : coins) {
      for (String currency : coin.getQuotes().keySet()) {
        try {
          updateCoinPriceChart(
              coin.getSymbol(), currency, coin.getQuotes().get(currency).getChart());
        } catch (Exception ex) {
          log.error(ex.getMessage());
        }
      }
    }
    log.info("Price list for {} coins has been successfully updated.", coins.size());
    return true;
  }
}
