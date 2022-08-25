package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.IApiCommunicatorFacade;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiHolder {

  private final Map<ApiProviderEnum, IApiCommunicatorFacade> apiCommunicators;

  @Autowired
  public ApiHolder(Set<IApiCommunicatorFacade> ApiCommunicatorFacadeSet) {
    // Strategies are sorted by apiProviderEnumOrder
    this.apiCommunicators = new TreeMap<>();
    ApiCommunicatorFacadeSet.forEach(
        strategy -> this.apiCommunicators.put(strategy.getApiProvider(), strategy));
  }

  public IApiCommunicatorFacade getApiCommunicator(ApiProviderEnum apiProviderEnum) {

    return this.apiCommunicators.get(apiProviderEnum);
  }

  public Result getCoinMarketData(
      List<String> coins, List<String> vsCurrencies) {

    return this.apiCommunicators.values().stream()
        .map(
            iApiCommunicatorFacade -> iApiCommunicatorFacade
                .getCurrentListing(coins, vsCurrencies))
        .filter(result -> result.getStatus().equals(ResultStatus.SUCCESS))
        .findFirst()
        .orElse(null);
    //    return results;
  }

  public Result getHistoricalCoinMarketData(List<String> coins, List<String> vsCurrencies, long timestampFrom, long timestampTo) {
    return this.apiCommunicators.values().stream()
        .map(
            iApiCommunicatorFacade -> iApiCommunicatorFacade
                .getHistoricalListing(
                    coins, vsCurrencies,
                    timestampFrom, timestampTo))
        .filter(result -> result.getStatus().equals(ResultStatus.SUCCESS))
        .findFirst()
        .orElse(null);
  }
  public Result getTopCoins(int take, List<String> vsCurrencies) {
    return this.apiCommunicators.values().stream()
        .map(
            iApiCommunicatorFacade -> iApiCommunicatorFacade
                .getTopCoins(take, vsCurrencies))
        .filter(result -> result.getStatus().equals(ResultStatus.SUCCESS))
        .findFirst()
        .orElse(null);
  }
}
