package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade.ApiCommunicatorFacadeTemplate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CoinMarketCapFacade extends ApiCommunicatorFacadeTemplate {
  private final ApiProviderEnum provider = ApiProviderEnum.COIN_MARKET_CAP;

  @Autowired
  public CoinMarketCapFacade(CoinMarketCapTranslator translator, CoinMarketCapApiClient client) {
    this.apiClient = client;
    this.coinTranslator = translator;
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
    List<String> translated =
        this.coinTranslator.translate(coins, ApiCommunicatorMethodEnum.CURRENT_LISTING);
    // translate symbols to whatever we need
    // use client to communicate with api
    // use resultbuilderdirector to build the result
    // return result

    return Optional.empty();
  }

  @Override
  public Optional<Result> getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo) {
    return Optional.empty();
  }
}
