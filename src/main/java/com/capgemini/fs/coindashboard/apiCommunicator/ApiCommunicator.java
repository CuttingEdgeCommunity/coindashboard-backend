package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResponse;
import java.util.List;

public interface ApiCommunicator {
  public ApiProviderEnum getApiProvider();
  public CoinMarketDataResponse getCurrentListing(List<String> coins, List<String> vsCurrencies);
  public CoinMarketDataResponse getHistoricalListing(List<String> coins, List<String> vsCurrencies, Long timestamp);
}
