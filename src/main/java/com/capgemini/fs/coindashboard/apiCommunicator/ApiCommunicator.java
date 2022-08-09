package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import java.util.List;

public interface ApiCommunicator {

  ApiProviderEnum getApiProvider();

  CoinMarketDataResult getCurrentListing(List<String> coins, List<String> vsCurrencies);

  CoinMarketDataResult getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestamp);
}
