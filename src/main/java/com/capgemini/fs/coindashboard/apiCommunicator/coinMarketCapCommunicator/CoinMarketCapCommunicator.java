package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoinMarketCapCommunicator implements ApiCommunicator {

  private static final Logger log = LogManager.getLogger(CoinMarketCapCommunicator.class);
  private final ApiProviderEnum providerEnum = ApiProviderEnum.COIN_MARKET_CAP;
  @Autowired private CoinMarketCapClient client;
  @Autowired private CoinMarketCapResponseParser parser;

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.providerEnum;
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
      return ret;

    } catch (IOException e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(
          this.providerEnum, ResultStatus.FAILURE, e.getMessage(), null);
    }
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
      return ret;

    } catch (IOException e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(
          this.providerEnum, ResultStatus.FAILURE, e.getMessage(), null);
    }
  }
}
