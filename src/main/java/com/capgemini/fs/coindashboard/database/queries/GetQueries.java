package com.capgemini.fs.coindashboard.database.queries;

import java.util.List;

public interface GetQueries {

  List<String> getAllCoins();

  List<String> getCoins(int take, int page);
}
