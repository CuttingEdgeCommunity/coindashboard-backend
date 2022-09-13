package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ApiCommunicatorMethodParametersDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.IApiMethods;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade.IApiCommunicatorFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiHolder implements IApiMethods {

  private final Map<ApiProviderEnum, IApiCommunicatorFacade> apiCommunicators;

  @Autowired
  public ApiHolder(Set<IApiCommunicatorFacade> apiCommunicatorFacades) {
    ArrayList<IApiCommunicatorFacade> orderedFacades = new ArrayList<>(apiCommunicatorFacades);
    orderedFacades.sort(Comparator.comparing(e -> e.getApiProvider().order));
    // Strategies are sorted by apiProviderEnumOrder
    this.apiCommunicators = new TreeMap<>();
    for (IApiCommunicatorFacade facade : orderedFacades) {
      this.apiCommunicators.put(facade.getApiProvider(), facade);
    }
  }

  public IApiCommunicatorFacade getApiCommunicator(ApiProviderEnum apiProviderEnum) {
    return this.apiCommunicators.get(apiProviderEnum);
  }

  private Optional<Result> execute(
      ApiCommunicatorMethodEnum methodEnum,
      List<ApiProviderEnum> providersToUse,
      ApiCommunicatorMethodParametersDto args) {
    return this.apiCommunicators.values().stream()
        .filter(provider -> providersToUse.contains(provider.getApiProvider()))
        .map(iApiCommunicatorFacade -> iApiCommunicatorFacade.executeMethod(methodEnum, args))
        .filter(result -> result != null && result.getStatus().equals(ResultStatus.SUCCESS))
        .findFirst();
  }

  @Override
  public Optional<Result> getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline) {
    return this.getCurrentListing(
        Arrays.asList(ApiProviderEnum.values()), coins, vsCurrencies, include7dSparkline);
  }

  public Optional<Result> getCurrentListing(
      List<ApiProviderEnum> providersToUse,
      List<String> coins,
      List<String> vsCurrencies,
      boolean include7dSparkline) {
    ApiCommunicatorMethodParametersDto params = new ApiCommunicatorMethodParametersDto();
    params.setCoins(coins);
    params.setVsCurrencies(vsCurrencies);
    params.setInclude7dSparkline(include7dSparkline);
    return this.execute(ApiCommunicatorMethodEnum.CURRENT_LISTING, providersToUse, params);
  }

  @Override
  public Optional<Result> getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo) {
    return this.getHistoricalListing(
        Arrays.asList(ApiProviderEnum.values()), coins, vsCurrencies, timestampFrom, timestampTo);
  }

  public Optional<Result> getHistoricalListing(
      List<ApiProviderEnum> providersToUse,
      List<String> coins,
      List<String> vsCurrencies,
      Long timestampFrom,
      Long timestampTo) {
    ApiCommunicatorMethodParametersDto params = new ApiCommunicatorMethodParametersDto();
    params.setCoins(coins);
    params.setVsCurrencies(vsCurrencies);
    params.setTimestampFrom(timestampFrom);
    params.setTimestampTo(timestampTo);
    return this.execute(ApiCommunicatorMethodEnum.HISTORICAL_LISTING, providersToUse, params);
  }

  @Override
  public Optional<Result> getTopCoins(int take, int page, List<String> vsCurrencies) {
    return this.getTopCoins(Arrays.asList(ApiProviderEnum.values()), take, page, vsCurrencies);
  }

  public Optional<Result> getTopCoins(
      List<ApiProviderEnum> providersToUse, int take, int page, List<String> vsCurrencies) {
    ApiCommunicatorMethodParametersDto params = new ApiCommunicatorMethodParametersDto();
    params.setTake(take);
    params.setPage(page);
    params.setVsCurrencies(vsCurrencies);
    return this.execute(ApiCommunicatorMethodEnum.TOP_COINS, providersToUse, params);
  }

  @Override
  public Optional<Result> getCoinInfo(List<String> coins) {
    return this.getCoinInfo(Arrays.asList(ApiProviderEnum.values()), coins);
  }

  public Optional<Result> getCoinInfo(List<ApiProviderEnum> providersToUse, List<String> coins) {
    ApiCommunicatorMethodParametersDto params = new ApiCommunicatorMethodParametersDto();
    params.setCoins(coins);
    return this.execute(ApiCommunicatorMethodEnum.COIN_INFO, providersToUse, params);
  }
}
