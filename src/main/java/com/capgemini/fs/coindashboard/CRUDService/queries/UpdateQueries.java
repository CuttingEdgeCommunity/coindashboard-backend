package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import java.util.List;

public interface UpdateQueries {

  boolean updateCoinCurrentQuote(String symbol, CurrentQuote newQuote, String vs_currency);

  boolean updateCoinCurrentQuoteAndMarketCapRank(
      String symbol, CurrentQuote newQuote, String vs_currency, Integer marketCapRank);

  boolean updateCoinsCurrentQuotesAndMarketCapRank(List<Coin> coins);

  boolean updateCoinPriceChart(String symbol, String vs_currency, List<Price> chart);

  boolean cleanCoinsMarketCapRanks(List<String> symbols);

  boolean updateTopCoinsTransaction(
      List<Coin> top_coins, List<String> kicked_from_top, List<Coin> marketCapRank_update);

  boolean updateEveryCoinPriceChart();

  boolean updateTopCoinsPriceChart(List<Coin> coins);

  void removeDuplicates(String id);
}
