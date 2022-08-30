package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.mongodb.client.result.UpdateResult;
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

  @Override
  public boolean UpdateCoinCurrentQuote(String symbol, CurrentQuote newQuote, String vs_currency) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("symbol").is(symbol));
      Update update = new Update();
      update.set("quotes." + vs_currency.toLowerCase() + ".currentQuote", newQuote);
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

  @Override
  public boolean UpdateCoinPriceChart(String symbol) {

    return false;
  }

  @Override
  public boolean UpdateCoinMarketRankCap(String symbol, Integer marketCapRank) {
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

  @Override
  public boolean UpdateEveryCoinPriceChart() {
    return false;
  }
}
