package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade.ApiCommunicatorFacadeTemplate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CoinGeckoFacade extends ApiCommunicatorFacadeTemplate {

  private final ApiProviderEnum provider = ApiProviderEnum.COIN_GECKO;

  @Autowired
  public CoinGeckoFacade(CoinGeckoTranslator translator, CoinGeckoApiClient client) {
    this.coinTranslator = translator;
    this.apiClient = client;
  }

  @Override
  public Result executeMethod(ApiCommunicatorMethodEnum method, Object... args) {
    return null;
  }

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.provider;
  }

  @Override
  public Optional<Result> getTopCoins(int take, int page, List<String> vsCurrencies) {
    return Optional.empty();
  }

  @Override
  public Optional<Result> getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline) {
    return Optional.empty();
  }

  @Override
  public Optional<Result> getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo) {
    return Optional.empty();
  }
}
