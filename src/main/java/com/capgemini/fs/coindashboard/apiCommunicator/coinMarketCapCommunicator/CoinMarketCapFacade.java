package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorFacadeTemplate;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import java.util.List;
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
  public void init() {}

  @Override
  public Result getTopCoins(Integer take, List<String> vsCurrencies) {
    return null;
  }

  @Override
  public Result getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline) {
    return null;
  }

  @Override
  public Result getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo) {
    return null;
  }
}
