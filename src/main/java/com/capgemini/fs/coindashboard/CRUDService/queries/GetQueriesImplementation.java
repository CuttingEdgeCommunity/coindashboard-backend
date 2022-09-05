package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.google.gson.Gson;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GetQueriesImplementation implements GetQueries {
  private static final Gson gson = new Gson();
  @Autowired private MongoTemplate mongoTemplate;

  @Override
  public String getAllCoins() {
    return gson.toJson(mongoTemplate.findAll(Coin.class, "Coin"));
  }

  @Override
  public String getCoinMarketData(
      String symbol, String vs_currency) { // TODO: check if vs_currency exists in the data base
    MatchOperation matchStage = Aggregation.match(new Criteria("symbol").is(symbol));
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
      log.error("coin" + symbol + " does not exist");
      return null;
    }
    log.info("passing coinMarketData from DB");
    return gson.toJson(result);
  }

  @Override
  public String getCoins(int take, int page) {
    MatchOperation matchStage =
        Aggregation.match(new Criteria("marketCapRank").gt(take * page).lte(take * (page + 1)));
    ProjectionOperation projectStage =
        Aggregation.project("name", "symbol", "image_url", "currentQuote")
            .and("quotes.usd.currentQuote")
            .as("CurrentQuote")
            .andExclude("_id");
    Aggregation aggregation =
        Aggregation.newAggregation(matchStage, projectStage, Aggregation.limit(take));
    List<Object> result =
        mongoTemplate.aggregate(aggregation, "Coin", Object.class).getMappedResults();
    if (result.isEmpty()) {
      log.error("coin matching criteria does not exist");
      return null;
    }
    log.info("passing coinMarketData from DB");
    return gson.toJson(result);
  }

  @Override
  public boolean isCoinInDBBySymbol(String symbol) {
    Query query = new Query();
    query.addCriteria(Criteria.where("symbol").is(symbol));
    return mongoTemplate.exists(query, Coin.class);
  }
}