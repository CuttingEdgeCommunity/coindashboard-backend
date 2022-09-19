package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import java.util.List;

public interface UpdateQueries {

  boolean updateCoinCurrentQuote(String symbol, CurrentQuote newQuote, String vs_currency);

  boolean updateCoinCurrentQuoteAndMarketCapRank(
      String symbol, CurrentQuote newQuote, String vs_currency, Integer marketCapRank);

  boolean updateCoinsCurrentQuotesAndMarketCapRank(List<Coin> coins);

  boolean UpdateCoinPriceChart(String symbol);

  boolean cleanCoinsMarketCapRanks(List<String> symbols);

  boolean updateTopCoinsTransaction(
      List<Coin> top_coins, List<String> kicked_from_top, List<Coin> marketCapRank_update);

  boolean UpdateEveryCoinPriceChart();

  void removeDuplicates(String id);
}
