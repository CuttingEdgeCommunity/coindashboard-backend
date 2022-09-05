package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.IApiMethods;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.IResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilderDirector;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.CoinTranslator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ApiCommunicatorFacadeTemplate implements IApiCommunicatorFacade, IApiMethods {
  protected CoinTranslator coinTranslator;
  protected Map<ApiCommunicatorMethodEnum, IResultBuilder> resultBuilders;
  protected ApiClient apiClient;
  @Autowired protected ResultBuilderDirector resultBuilderDirector;

  @PostConstruct
  public void init() {}

  public Result executeMethod(ApiCommunicatorMethodEnum method, Object... args) {
    var result =
        switch (method) {
          case TOP_COINS -> this.getTopCoins((int) args[0], (int) args[1], (List<String>) args[2]);
          case CURRENT_LISTING -> this.getCurrentListing(
              (List<String>) args[0], (List<String>) args[1], (boolean) args[2]);
          case HISTORICAL_LISTING -> this.getHistoricalListing(
              (List<String>) args[0], (List<String>) args[1], (long) args[2], (long) args[3]);
          case COIN_INFO -> this.getCoinInfo((List<String>) args[0]);
        };
    return result.orElse(null);
  }
}
