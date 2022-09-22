package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.CoinMarketCapBuilderBaseClass;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ApiCommunicatorMethodParametersDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade.ApiCommunicatorFacadeTemplate;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.IResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public final class CoinMarketCapFacade extends ApiCommunicatorFacadeTemplate {
  private final ApiProviderEnum provider = ApiProviderEnum.COIN_MARKET_CAP;

  @Autowired
  public CoinMarketCapFacade(
      CoinMarketCapTranslator translator,
      CoinMarketCapApiClient client,
      Set<CoinMarketCapBuilderBaseClass> builders) {
    this.apiClient = client;
    this.coinTranslator = translator;
    this.resultBuilders =
        builders.stream().collect(Collectors.toMap(IResultBuilder::getMethod, Function.identity()));
  }

  @Override
  public void init() {
    try {
      this.coinTranslator.initialize(((CoinMarketCapApiClient) this.apiClient).getCoinsNames());
    } catch (Exception e) {
      log.info("Error with translator initialization...");
    }
  }

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.provider;
  }

  @Override
  public Optional<Result> getTopCoins(int take, int page, List<String> vsCurrencies, boolean include7dSparkline) {
    try {
      Response response =
          ((CoinMarketCapApiClient) this.apiClient).getTopCoins(take, page, vsCurrencies);
      this.resultBuilderDirector.constructCoinMarketDataResult(
          this.resultBuilders.get(ApiCommunicatorMethodEnum.TOP_COINS),
          response,
          new ApiCommunicatorMethodParametersDto(take, page, vsCurrencies,include7dSparkline));
      return Optional.of(this.resultBuilders.get(ApiCommunicatorMethodEnum.TOP_COINS).getResult());
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }

  @Override
  public Optional<Result> getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline) {
    try {
      Response response =
          ((CoinMarketCapApiClient) this.apiClient)
              .getCurrentListing(coins, vsCurrencies, include7dSparkline);
      this.resultBuilderDirector.constructCoinMarketDataResult(
          this.resultBuilders.get(ApiCommunicatorMethodEnum.CURRENT_LISTING),
          response,
          new ApiCommunicatorMethodParametersDto(coins, vsCurrencies, include7dSparkline));
      return Optional.of(
          this.resultBuilders.get(ApiCommunicatorMethodEnum.CURRENT_LISTING).getResult());
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }

  @Override
  public Optional<Result> getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo) {
    try {
      Response response =
          ((CoinMarketCapApiClient) this.apiClient)
              .getHistoricalListing(coins, vsCurrencies, timestampFrom, timestampTo);
      this.resultBuilderDirector.constructCoinMarketDataResult(
          this.resultBuilders.get(ApiCommunicatorMethodEnum.HISTORICAL_LISTING),
          response,
          new ApiCommunicatorMethodParametersDto(coins, vsCurrencies, timestampFrom, timestampTo));
      return Optional.of(
          this.resultBuilders.get(ApiCommunicatorMethodEnum.HISTORICAL_LISTING).getResult());
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }

  @Override
  public Optional<Result> getCoinInfo(List<String> coins) {
    try {
      List<String> coinsTranslated =
          this.coinTranslator.translate(coins, ApiCommunicatorMethodEnum.COIN_INFO);
      Response response = ((CoinMarketCapApiClient) this.apiClient).getCoinInfo(coinsTranslated);
      this.resultBuilderDirector.constructCoinMarketDataResult(
          this.resultBuilders.get(ApiCommunicatorMethodEnum.COIN_INFO),
          response,
          new ApiCommunicatorMethodParametersDto(coins));
      return Optional.of(this.resultBuilders.get(ApiCommunicatorMethodEnum.COIN_INFO).getResult());
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }
}
