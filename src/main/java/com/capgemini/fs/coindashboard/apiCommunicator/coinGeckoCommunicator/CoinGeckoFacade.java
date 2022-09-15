package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders.CoinGeckoBuilderBaseClass;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade.ApiCommunicatorFacadeTemplate;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.IResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.util.ArrayList;
import java.util.HashMap;
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
public final class CoinGeckoFacade extends ApiCommunicatorFacadeTemplate {

  private final ApiProviderEnum provider = ApiProviderEnum.COIN_GECKO;

  @Autowired
  public CoinGeckoFacade(
      CoinGeckoTranslator translator,
      CoinGeckoApiClient client,
      Set<CoinGeckoBuilderBaseClass> builders) {
    this.coinTranslator = translator;
    this.apiClient = client;
    this.resultBuilders =
        builders.stream().collect(Collectors.toMap(IResultBuilder::getMethod, Function.identity()));
  }

  @Override
  public void init() {
    try {
      this.coinTranslator.initialize(((CoinGeckoApiClient) this.apiClient).getCoinsNames());
    } catch (Exception e) {
      log.info("Error with translator initialization...");
    }
  }

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.provider;
  }

  @Override
  public Optional<Result> getTopCoins(int take, int page, List<String> vsCurrencies) {

    try {
      Result result = new Result(this.provider, ResultStatus.FAILURE, null, null);
      List<String> errorMessages = new ArrayList<>();

      for (String vsCurrency : vsCurrencies) {
        Response response =
            ((CoinGeckoApiClient) this.apiClient).getTopCoins(take, page, vsCurrency);
        this.resultBuilderDirector.constructCoinMarketDataResult(
            this.resultBuilders.get(ApiCommunicatorMethodEnum.TOP_COINS),
            response,
            take,
            page,
            vsCurrency);
        Result responseResult =
            this.resultBuilders.get(ApiCommunicatorMethodEnum.TOP_COINS).getResult();
        if (responseResult.getStatus() == ResultStatus.FAILURE) {
          if (result.getStatus() == ResultStatus.SUCCESS)
            result.setStatus(ResultStatus.PARTIAL_SUCCESS);
          errorMessages.add(responseResult.getErrorMessage());
          continue;
        }
        if (result.getStatus() == ResultStatus.FAILURE) {
          result.setStatus(ResultStatus.SUCCESS);
          result.setCoins(responseResult.getCoins());
          continue;
        }
        for (int i = 0; i < result.getCoins().size(); i++) {
          result
              .getCoins()
              .get(i)
              .getQuotes()
              .put(vsCurrency, responseResult.getCoins().get(i).getQuotes().get(vsCurrency));
        }
      }
      if (!errorMessages.isEmpty()) result.setErrorMessage(String.join(", ", errorMessages));

      return Optional.of(result);
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }

  @Override
  public Optional<Result> getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline) {
    // coinTranslator.translate(coins, ApiCommunicatorMethodEnum.CURRENT_LISTING);

    try {
      Result result = new Result(this.provider, ResultStatus.FAILURE, null, new ArrayList<>());
      List<String> errorMessages = new ArrayList<>();

      for (String coin : coins) {
        Response response =
            ((CoinGeckoApiClient) this.apiClient).getCurrentListing(coin, include7dSparkline);
        this.resultBuilderDirector.constructCoinMarketDataResult(
            this.resultBuilders.get(ApiCommunicatorMethodEnum.CURRENT_LISTING),
            response,
            coin,
            vsCurrencies);
        Result responseResult =
            this.resultBuilders.get(ApiCommunicatorMethodEnum.CURRENT_LISTING).getResult();
        if (responseResult.getStatus() == ResultStatus.FAILURE) {
          if (result.getStatus() == ResultStatus.SUCCESS)
            result.setStatus(ResultStatus.PARTIAL_SUCCESS);
          errorMessages.add(responseResult.getErrorMessage());
          continue;
        }
        if (result.getStatus() == ResultStatus.FAILURE) result.setStatus(ResultStatus.SUCCESS);
        result.getCoins().add(responseResult.getCoins().get(0));
      }
      if (!errorMessages.isEmpty()) result.setErrorMessage(String.join(", ", errorMessages));
      if (result.getCoins().isEmpty()) result.setCoins(null);

      return Optional.of(result);
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }

  @Override
  public Optional<Result> getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo) {
    // coinTranslator.translate(coins, ApiCommunicatorMethodEnum.HISTORICAL_LISTING);

    try {
      Result result = new Result(this.provider, ResultStatus.FAILURE, null, new ArrayList<>());
      List<String> errorMessages = new ArrayList<>();

      for (String coin : coins) {
        Coin coinTemplate = new Coin();
        coinTemplate.setName(coin);
        coinTemplate.setQuotes(new HashMap<>());
        result.getCoins().add(coinTemplate);
        for (String vsCurrency : vsCurrencies) {
          Response response =
              ((CoinGeckoApiClient) this.apiClient)
                  .getHistoricalListing(coin, vsCurrency, timestampFrom, timestampTo);
          this.resultBuilderDirector.constructCoinMarketDataResult(
              this.resultBuilders.get(ApiCommunicatorMethodEnum.HISTORICAL_LISTING),
              response,
              coin,
              vsCurrency);
          Result responseResult =
              this.resultBuilders.get(ApiCommunicatorMethodEnum.HISTORICAL_LISTING).getResult();
          if (responseResult.getStatus() == ResultStatus.FAILURE) {
            if (result.getStatus() == ResultStatus.SUCCESS)
              result.setStatus(ResultStatus.PARTIAL_SUCCESS);
            errorMessages.add(responseResult.getErrorMessage());
            continue;
          }
          if (result.getStatus() == ResultStatus.FAILURE) result.setStatus(ResultStatus.SUCCESS);
          result
              .getCoins()
              .get(result.getCoins().size() - 1)
              .getQuotes()
              .put(vsCurrency, responseResult.getCoins().get(0).getQuotes().get(vsCurrency));
        }
      }
      if (!errorMessages.isEmpty()) result.setErrorMessage(String.join(", ", errorMessages));
      if (result.getCoins().isEmpty()) result.setCoins(null);

      return Optional.of(result);
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }

  @Override
  public Optional<Result> getCoinInfo(List<String> coins) {
    coins = coinTranslator.translate(coins, ApiCommunicatorMethodEnum.COIN_INFO);

    try {
      Result result = new Result(this.provider, ResultStatus.FAILURE, null, new ArrayList<>());
      List<String> errorMessages = new ArrayList<>();

      for (int i = 0; i < coins.size() - 1; i++) {
        if (i % 48 == 0 && i != 0) {
          log.info("Sleeping for 1 minute...{}", i);
          Thread.sleep(60000);
        }
        Response response = ((CoinGeckoApiClient) this.apiClient).getCoinInfo(coins.get(i));
        log.info("Iteration: {}", i);
        this.resultBuilderDirector.constructCoinMarketDataResult(
            this.resultBuilders.get(ApiCommunicatorMethodEnum.COIN_INFO), response, coins.get(i));
        Result responseResult =
            this.resultBuilders.get(ApiCommunicatorMethodEnum.COIN_INFO).getResult();
        if (responseResult.getStatus() == ResultStatus.FAILURE) {
          if (result.getStatus() == ResultStatus.SUCCESS)
            result.setStatus(ResultStatus.PARTIAL_SUCCESS);
          errorMessages.add(responseResult.getErrorMessage());
          continue;
        }
        if (result.getStatus() == ResultStatus.FAILURE) result.setStatus(ResultStatus.SUCCESS);
        result.getCoins().add(responseResult.getCoins().get(0));
      }
      if (!errorMessages.isEmpty()) result.setErrorMessage(String.join(", ", errorMessages));
      if (result.getCoins().isEmpty()) result.setCoins(null);

      return Optional.of(result);
    } catch (Exception e) {
      return Optional.of(new Result(this.provider, ResultStatus.FAILURE, e.getMessage(), null));
    }
  }
}
