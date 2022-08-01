package com.capgemini.fs.apiCommunicator.interfaces;

import com.capgemini.fs.apiCommunicator.dtos.marketData.CoinMarketDataDTO;
import com.capgemini.fs.apiCommunicator.dtos.marketData.CoinMarketDataResponse;
import java.util.List;

public interface ApiCommunicator {
  public CoinMarketDataResponse getCurrentListing(List<String> coins, List<String> vsCurrencies);
  public CoinMarketDataResponse getHistoricalListing(List<String> coins, List<String> vsCurrencies, Long timestamp);
}
