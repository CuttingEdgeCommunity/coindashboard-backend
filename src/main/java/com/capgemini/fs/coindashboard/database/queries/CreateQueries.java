package com.capgemini.fs.coindashboard.database.queries;

import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;

public interface CreateQueries {

  boolean CreateCoinDocument(CoinMarketDataDto coinMarketDataDto);
}
