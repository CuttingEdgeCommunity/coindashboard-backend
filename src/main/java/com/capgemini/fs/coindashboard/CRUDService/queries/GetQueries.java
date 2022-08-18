package com.capgemini.fs.coindashboard.CRUDService.queries;

public interface GetQueries {

  String getAllCoins();

  String getCoinMarketData(String name, String vs_currency);

  String getCoins(int take, int page);
}
