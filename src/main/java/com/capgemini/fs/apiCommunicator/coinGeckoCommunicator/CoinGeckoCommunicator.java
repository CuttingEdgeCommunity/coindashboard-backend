package com.capgemini.fs.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.apiCommunicator.dtos.marketData.CoinMarketDataDTO;
import com.capgemini.fs.apiCommunicator.dtos.marketData.CoinMarketDataResponse;
import com.capgemini.fs.apiCommunicator.interfaces.ApiCommunicator;
import java.util.List;

public class CoinGeckoCommunicator implements ApiCommunicator {
  CoinGeckoClient coinGeckoClient = new CoinGeckoClient();
  @Override
  public CoinMarketDataResponse getCurrentListing(List<String> coins, List<String> vsCurrencies) {
    return null;
  }

  @Override
  public CoinMarketDataResponse getHistoricalListing(List<String> coins, List<String> vsCurrencies,
      Long timestamp) {
    return null;
  }
}
