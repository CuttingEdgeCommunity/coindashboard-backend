package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.queries.Utils.Passers;
import com.capgemini.fs.coindashboard.controller.ControllerConfig;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class Queries implements UpdateQueries, GetQueries, CreateQueries {
  private static final Logger log = LogManager.getLogger(ControllerConfig.class);
  private static final Gson gson = new Gson();
  @Autowired private MongoTemplate mongoTemplate;

  @Override
  public String getAllCoins() {
    return gson.toJson(mongoTemplate.findAll(Coin.class, "Coin"));
  }

  @Override
  public String getCoins(int take, int page) {
    Query query = new Query();
    query.with(PageRequest.of(page, take));
    query
        .fields()
        .include("name")
        .include("symbol")
        // .include("image_url")
        .include("quotes.usd.currentQuote");

    return gson.toJson(mongoTemplate.find(query, Coin.class, "Coin"));
  }

  @Override
  public boolean UpdateCoinCurrentQuote(String coinName, QuoteDto newQuote, String vs_currency) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where("name").is(coinName));
      Update update = new Update();
      update.set(
          "quotes." + vs_currency.toLowerCase() + ".currentQuote",
          Passers.fromQuoteDtoToCurrentQuote(newQuote));
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
  public boolean UpdateCoinPriceChart(String coinName) {
    return false;
  }

  @Override
  public boolean UpdateEveryCoinPriceChart() {
    return false;
  }

  @Override
  public boolean CreateCoinDocument(CoinMarketDataDto coinMarketDataDto) {
    try {
      mongoTemplate.save(Passers.fromCoinMarketDataDtoToCoin(coinMarketDataDto));
      return true;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }
}
