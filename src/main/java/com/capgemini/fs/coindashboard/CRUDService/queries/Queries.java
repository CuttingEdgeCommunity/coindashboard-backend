package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.queries.Utils.Passers;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Queries implements UpdateQueries, GetQueries, CreateQueries {
  private static final Gson gson = new Gson();
  @Autowired private MongoTemplate mongoTemplate;

  @Override
  public String getAllCoins() {
    return gson.toJson(mongoTemplate.findAll(Coin.class, "Coin"));
  }

  @Override
  public String getCoinMarketData(
      String name, String vs_currency) { // TODO: check if vs_currency exists in the data base
    MatchOperation matchStage = Aggregation.match(new Criteria("name").is(name));
    ProjectionOperation projectStage =
        Aggregation.project("name", "symbol", "currentQuote")
            .and("quotes." + vs_currency + ".currentQuote")
            .as("CurrentQuote")
            .andExclude("_id");
    Aggregation aggregation =
        Aggregation.newAggregation(matchStage, projectStage, Aggregation.limit(1));
    List<Object> result =
        mongoTemplate.aggregate(aggregation, "Coin", Object.class).getMappedResults();
    if (result.isEmpty()) {
      log.error("coin" + name + " does not exist");
      return null;
    }
    log.info("passing coinMarketData from DB");
    return gson.toJson(result);
  }

  @Override
  public String getCoins(int take, int page) {
    MatchOperation matchStage = Aggregation.match(new Criteria("name").is("Bitcoin"));
    ProjectionOperation projectStage =
        Aggregation.project("name", "symbol", "currentQuote")
            .and("quotes.usd.currentQuote")
            .as("CurrentQuote")
            .andExclude("_id");
    Aggregation aggregation =
        Aggregation.newAggregation(matchStage, projectStage, Aggregation.limit(1));

    return gson.toJson(
        mongoTemplate.aggregate(aggregation, "Coin", Object.class).getMappedResults());
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
