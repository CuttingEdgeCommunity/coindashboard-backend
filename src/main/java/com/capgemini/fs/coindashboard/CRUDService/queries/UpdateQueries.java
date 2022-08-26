package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;

public interface UpdateQueries {

  boolean updateCoinCurrentQuote(String coinName, QuoteDto newQuote, String vs_currency);

  boolean updateCoinPriceChart(String coinName);

  boolean updateEveryCoinPriceChart();
}
