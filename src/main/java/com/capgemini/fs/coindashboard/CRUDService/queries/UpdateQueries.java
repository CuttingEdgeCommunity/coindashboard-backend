package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;

public interface UpdateQueries {

  boolean updateCoinCurrentQuote(String symbol, CurrentQuote newQuote, String vs_currency);

  boolean updateCoinsCurrentQuotes(Result result);

  boolean UpdateCoinPriceChart(String symbol);

  boolean UpdateCoinMarketCapRank(String symbol, Integer marketCapRank);

  boolean cleanCoinsMarketCapRanks(int coinsAmount);

  boolean updateTopCoinsTransaction(Result result);

  boolean UpdateEveryCoinPriceChart();
}
