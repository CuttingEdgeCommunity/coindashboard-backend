package com.capgemini.fs.coindashboard.CRUDService.queries;


public interface GetQueries {

  String getAllCoins();

  String getCoins(int take, int page);
}
