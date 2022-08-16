package com.capgemini.fs.coindashboard.database.queries;

import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class Queries implements UpdateQueries, GetQueries, CreateQueries {

  @Autowired
  private MongoTemplate mongoTemplate;

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
  public List<String> getAllCoins() {
    return null;
  }

  @Override
  public List<String> getCoins(int take, int page) {
    return null;
  }
}
