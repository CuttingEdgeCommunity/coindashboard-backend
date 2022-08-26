package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.CoinTranslator;
import java.util.List;
import java.util.Map;

public abstract class ApiCommunicatorFacadeTemplate implements IApiCommunicatorFacade {
  protected CoinTranslator coinTranslator;
  protected Map<ApiCommunicatorMethodEnum, ResultBuilder> resultBuilders;
  protected ApiClient apiClient;

  protected abstract void init();

  @Override
  public ApiProviderEnum getApiProvider() {
    return null;
  }

  protected abstract Result getTopCoins(Integer take, List<String> vsCurrencies);

  protected abstract Result getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline);

  protected abstract Result getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo);

  // TODO: think about this, maybe move the responsibility from api holder
  public Result executeMethod(ApiCommunicatorMethodEnum method, Object... args) {
    return switch (method) {
      case TOP_COINS -> this.getTopCoins((int) args[0], (List<String>) args[1]);
      case CURRENT_LISTING -> this.getCurrentListing(
          (List<String>) args[0], (List<String>) args[1], (boolean) args[2]);
      case HISTORICAL_LISTING -> this.getHistoricalListing(
          (List<String>) args[0], (List<String>) args[1], (long) args[2], (long) args[3]);
    };
  }
}
