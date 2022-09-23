package com.capgemini.fs.coindashboard.CRUDService.queries;

public interface GetQueries {

  String getAllCoins();

  String getCoinMarketData(String symbol, String vs_currency);

  String getCoinDetails(String symbol);

  String getCoins(int take, int page);

  String getCoinsSimple(int take, int page);

  boolean isCoinInDBBySymbol(String name);

  String findCoinByRegex(String query);

  String getChart(String symbol, Long chartFrom, Long chartTo);
}
