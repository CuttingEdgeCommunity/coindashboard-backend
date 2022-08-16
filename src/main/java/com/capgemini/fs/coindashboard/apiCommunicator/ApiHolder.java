package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiHolder {

  private final Map<ApiProviderEnum, ApiCommunicator> apiCommunicators;

  @Autowired
  public ApiHolder(Set<ApiCommunicator> apiCommunicatorSet) {
    // Strategies are sorted by apiProviderEnumOrder
    this.apiCommunicators = new TreeMap<>();
    apiCommunicatorSet.forEach(
        strategy -> this.apiCommunicators.put(strategy.getApiProvider(), strategy));
  }

  public ApiCommunicator getApiCommunicator(ApiProviderEnum apiProviderEnum) {
    return this.apiCommunicators.get(apiProviderEnum);
  }

  public CoinMarketDataResult getCoinMarketData(
      String coinName) { // TODO: handle crypto names (ie. btc = bitcoin = 1 inCMC)

    return this.apiCommunicators.entrySet().stream()
        .map(
            api ->
                api.getValue()
                    .getCurrentListing(
                        new ArrayList<>(
                            List.of(
                                api.getKey() == ApiProviderEnum.COIN_GECKO ? "bitcoin" : coinName)),
                        new ArrayList<>(List.of("usd"))))
        .filter(result -> result.getStatus().equals(ResultStatus.SUCCESS))
        .findFirst()
        .orElse(null);
    //    return results;
  }

  public CoinMarketDataResult getHistoricalCoinMarketData(String coinName, long timestamp) {
    return this.apiCommunicators.entrySet().stream()
        .map(
            api ->
                api.getValue()
                    .getHistoricalListing(
                        new ArrayList<>(
                            List.of(
                                api.getKey() == ApiProviderEnum.COIN_GECKO ? "bitcoin" : coinName)),
                        new ArrayList<>(List.of("usd")),
                        timestamp))
        .filter(result -> result.getStatus().equals(ResultStatus.SUCCESS))
        .findFirst()
        .orElse(null);
  }
}
