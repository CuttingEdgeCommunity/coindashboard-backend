package com.capgemini.fs.coindashboard.apiCommunicator.dtos.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CoinMarketCapCommunicator implements ApiCommunicator {

  private final ApiProviderEnum providerEnum = ApiProviderEnum.COIN_MARKET_CAP;
  @Autowired private CoinMarketCapClient client;
  @Autowired private CoinMarketCapResponseParser parser;

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.providerEnum;
  }

  @Override
  public CoinMarketDataResult getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestamp) {
    try {
      Response response = this.client.getHistoricalCoinQuotes(coins, vsCurrencies, timestamp);

      var ret = parser.parseQuoteHistoricalResult(response.getResponseBody());
      ret.setProvider(this.providerEnum);
      ret.setStatus(
          ret.getCoinMarketDataDTOS() != null
              && ret.getCoinMarketDataDTOS().size() != coins.size());
      log.info(
          "Loaded historical listing for coins: "
              + String.join(",", coins)
              + " with status: "
              + ret.getStatus());
      return ret;
    } catch (IOException e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(
          this.providerEnum, ResultStatus.FAILURE, e.getMessage(), null);
    }
  }

  @Override
  public CoinMarketDataResult getCurrentListing(List<String> coins, List<String> vsCurrencies) {
    try {
      Response response = this.client.getCoinQuotes(coins, vsCurrencies);
      var ret = parser.parseQuoteLatestResult(response.getResponseBody());
      ret.setProvider(this.providerEnum);
      ret.setStatus(
          ret.getCoinMarketDataDTOS() != null
              && ret.getCoinMarketDataDTOS().size() != coins.size());
      log.info(
          "Loaded current listing for coins: "
              + String.join(",", coins)
              + " with status: "
              + ret.getStatus());
      return ret;
    } catch (IOException e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(
          this.providerEnum, ResultStatus.FAILURE, e.getMessage(), null);
    }
  }
}