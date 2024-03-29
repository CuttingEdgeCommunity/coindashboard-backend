package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${controller.search-limit}")
  private int searchLimit;

  @Override
  public String getAllCoins() {
    return gson.toJson(mongoTemplate.findAll(Coin.class, "Coin"));
  }

  @Override
  public String getCoinMarketData(
      String symbol, String vs_currency) { // TODO: check if vs_currency exists in the data base
    MatchOperation matchStage = Aggregation.match(new Criteria("symbol").is(symbol));
    ProjectionOperation projectStage =
        Aggregation.project("name", "symbol", "marketCapRank", "currentQuote")
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
  public String getCoinDetails(String symbol) {
    MatchOperation matchStage = Aggregation.match(new Criteria("symbol").is(symbol));
    ProjectionOperation projectStage =
        Aggregation.project(
                "name",
                "symbol",
                "image_url",
                "genesis_date",
                "is_token",
                "contract_address",
                "links",
                "description")
            .andExclude("_id");
    Aggregation aggregation =
        Aggregation.newAggregation(matchStage, projectStage, Aggregation.limit(1));
    List<Object> result =
        mongoTemplate.aggregate(aggregation, "Coin", Object.class).getMappedResults();
    if (result.isEmpty()) {
      log.error("coin" + symbol + " does not exist");
      return null;
    }
    log.info("passing CoinDetails from DB");
    return gson.toJson(result);
  }

  public String getCoinsSimple(int take, int page) {
    Query query = new Query(new Criteria("marketCapRank").gt(take * page).lte(take * (page + 1)));
    query.fields().include("id", "symbol", "marketCapRank");
    return (gson.toJson(mongoTemplate.find(query, Coin.class, "Coin")));
  }

  @Override
  public String getCoins(int take, int page) {
    MatchOperation matchStage =
        Aggregation.match(new Criteria("marketCapRank").gt(take * page).lte(take * (page + 1)));
    ProjectionOperation projectStage =
        Aggregation.project("name", "symbol", "marketCapRank", "image_url", "currentQuote")
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
    log.info("passing Coins from DB");
    return gson.toJson(result);
  }

  @Override
  public boolean isCoinInDBBySymbol(String symbol) {
    Query query = new Query();
    query.addCriteria(Criteria.where("symbol").is(symbol));
    return mongoTemplate.exists(query, Coin.class);
  }

  @Override
  public String findCoinByRegex(String query) {
    MatchOperation matchStage =
        Aggregation.match(
            new Criteria()
                .orOperator(
                    new Criteria("symbol").regex(query), new Criteria("name").regex(query)));
    ProjectionOperation projectStage =
        Aggregation.project("name", "symbol", "marketCapRank", "image_url", "currentQuote")
            .and("quotes.usd.currentQuote")
            .as("CurrentQuote")
            .andExclude("_id");
    Aggregation aggregation =
        Aggregation.newAggregation(matchStage, projectStage, Aggregation.limit(searchLimit));
    List<Object> result =
        mongoTemplate.aggregate(aggregation, "Coin", Object.class).getMappedResults();
    if (result.isEmpty()) {
      log.error("coin matching regex: " + query + " does not exist");
      return null;
    }
    log.info("passing found coins from DB for regex: " + query);
    return gson.toJson(result);
  }

  @Override
  public String getChart(String symbol, Long chartFrom, Long chartTo) {
    MatchOperation matchStage = Aggregation.match(new Criteria("symbol").is(symbol));
    ProjectionOperation projectStage =
        Aggregation.project("chart").and("quotes.usd.chart").as("chart").andExclude("_id");
    Aggregation aggregation =
        Aggregation.newAggregation(matchStage, projectStage, Aggregation.limit(searchLimit));
    List<Object> result =
        mongoTemplate.aggregate(aggregation, "Coin", Object.class).getMappedResults();
    if (result.isEmpty()) {
      log.error("coin: " + symbol + " does not exist");
      return null;
    }
    log.info("passing chart from DB for symbol: " + symbol);
    //    List chart = (List)((Map<?, ?>) result.get(0)).get("chart");
    //    Collections.reverse(chart);
    //    return gson.toJson(chart);
    return gson.toJson(((Map<?, ?>) result.get(0)).get("chart"));
  }
}
