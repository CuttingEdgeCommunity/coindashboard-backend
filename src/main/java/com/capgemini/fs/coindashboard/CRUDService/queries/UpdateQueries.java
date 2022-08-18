package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;

public interface UpdateQueries {

  boolean UpdateCoinCurrentQuote(String coinName, QuoteDto newQuote, String vs_currency);

  boolean UpdateCoinPriceChart(String coinName);

  boolean UpdateEveryCoinPriceChart();
}
