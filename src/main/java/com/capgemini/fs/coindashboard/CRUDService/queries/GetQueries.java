package com.capgemini.fs.coindashboard.CRUDService.queries;

public interface GetQueries {

  String getAllCoins();

  String getCoinMarketData(String symbol, String vs_currency);

  String getCoinDetails(String symbol);

  String getCoins(int take, int page);

  boolean isCoinInDBBySymbol(String name);
}
