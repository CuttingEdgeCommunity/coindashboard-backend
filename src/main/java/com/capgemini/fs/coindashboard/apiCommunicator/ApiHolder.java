package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.IApiCommunicatorFacade;
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

  private Result execute(ApiCommunicatorMethodEnum methodEnum, Object... args) {
    return this.apiCommunicators.values().stream()
        .map(iApiCommunicatorFacade -> iApiCommunicatorFacade.executeMethod(methodEnum, args))
        .filter(result -> result.getStatus().equals(ResultStatus.SUCCESS))
        .findFirst()
        .orElse(null);
  }

  public Result getCoinMarketData(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline) {
    return this.execute(
        ApiCommunicatorMethodEnum.CURRENT_LISTING, coins, vsCurrencies, include7dSparkline);
  }

  public Result getHistoricalCoinMarketData(
      List<String> coins, List<String> vsCurrencies, long timestampFrom, long timestampTo) {
    return this.execute(
        ApiCommunicatorMethodEnum.HISTORICAL_LISTING,
        coins,
        vsCurrencies,
        timestampFrom,
        timestampTo);
  }

  public Result getTopCoins(int take, List<String> vsCurrencies) {
    return this.execute(ApiCommunicatorMethodEnum.TOP_COINS, take, vsCurrencies);
  }
}
