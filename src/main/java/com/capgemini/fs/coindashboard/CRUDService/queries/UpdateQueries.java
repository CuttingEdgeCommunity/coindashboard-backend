package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;

public interface UpdateQueries {

  boolean UpdateCoinCurrentQuote(String symbol, CurrentQuote newQuote, String vs_currency);

  boolean UpdateCoinPriceChart(String symbol);

  boolean UpdateCoinMarketRankCap(String symbol, Integer marketCapRank);

  boolean UpdateEveryCoinPriceChart();
}
