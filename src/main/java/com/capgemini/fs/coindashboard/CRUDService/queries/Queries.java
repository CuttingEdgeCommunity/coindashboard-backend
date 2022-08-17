package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.queries.Utils.Passers;
import com.capgemini.fs.coindashboard.controller.ControllerConfig;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class Queries implements UpdateQueries, GetQueries, CreateQueries {
  private static final Logger log = LogManager.getLogger(ControllerConfig.class);
  @Autowired private MongoTemplate mongoTemplate;

  @Override
  public List<String> getAllCoins() {
    return null;
  }

  @Override
  public List<String> getCoins(int take, int page) {
    return null;
  }

  @Override
  public boolean UpdateCoinCurrentQuote(String coinName, QuoteDto newQuote) {
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
