package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ApiCommunicatorMethodParametersDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.IApiMethods;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.IResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilderDirector;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.CoinTranslator;
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

  @Override
  public Result executeMethod(
      ApiCommunicatorMethodEnum method, ApiCommunicatorMethodParametersDto args) {
    var result =
        switch (method) {
          case TOP_COINS -> this.getTopCoins(
              args.getTake(), args.getPage(), args.getVsCurrencies());
          case CURRENT_LISTING -> this.getCurrentListing(
              args.getCoins(), args.getVsCurrencies(), args.isInclude7dSparkline());
          case HISTORICAL_LISTING -> this.getHistoricalListing(
              args.getCoins(),
              args.getVsCurrencies(),
              args.getTimestampFrom(),
              args.getTimestampTo());
          case COIN_INFO -> this.getCoinInfo(args.getCoins());
          default -> throw new UnsupportedOperationException(
              "Unsupported method: " + method.name());
        };
    return result.orElse(null);
  }
}
