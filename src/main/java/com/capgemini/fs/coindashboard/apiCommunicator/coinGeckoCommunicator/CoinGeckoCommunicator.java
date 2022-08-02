package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiCommunicator;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CoinGeckoCommunicator implements ApiCommunicator {
  ApiClient client = new ApiClient();
  ApiProviderEnum apiProvider = ApiProviderEnum.COIN_GECKO;

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.apiProvider;
  }

  @Override
  public CoinMarketDataResult getCurrentListing(List<String> coins, List<String> vsCurrencies) {
    return null;
  }

  @Override
  public CoinMarketDataResult getHistoricalListing(List<String> coins, List<String> vsCurrencies,
      Long timestamp) {
    return null;
  }
}
