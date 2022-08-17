package com.capgemini.fs.coindashboard.CRUDService.queries;

import java.util.List;

public interface GetQueries {

  List<String> getAllCoins();

  List<String> getCoins(int take, int page);
}
