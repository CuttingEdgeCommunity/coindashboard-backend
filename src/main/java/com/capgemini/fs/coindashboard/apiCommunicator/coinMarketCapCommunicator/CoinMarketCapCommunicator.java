package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CoinMarketCapCommunicator implements ApiCommunicator {

  private final ApiProviderEnum providerEnum = ApiProviderEnum.COIN_MARKET_CAP;
  //  @Autowired
  private final CoinMarketCapClient client = new CoinMarketCapClient();
  //  @Autowired
  private final CoinMarketCapResponseParser parser = new CoinMarketCapResponseParser();
  private static final Logger log = LogManager.getLogger(CoinMarketCapCommunicator.class);

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.providerEnum;
  }

  @Override
  public CoinMarketDataResult getCurrentListing(List<String> coins, List<String> vsCurrencies) {
    try {
      Response response = this.client.getCoinQuotes(coins, vsCurrencies);
      if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
        log.error(response.getResponseBody().get("error").asText());
        return new CoinMarketDataResult(this.providerEnum, ResultStatus.FAILURE,
            response.getResponseBody().get("error").asText(), null);
      }

      var ret = parser.parseGetCoinsQuoteResult(response);
      ret.setProvider(this.providerEnum);
      if (ret.getStatus() == ResultStatus.SUCCESS &&
          ret.getCoinMarketDataDTOS().size() != coins.size()) {
        ret.setStatus(ResultStatus.PARTIAL_SUCCESS);
      }
      return ret;

    } catch (IOException e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(this.providerEnum, ResultStatus.FAILURE, e.getMessage(),
          null);
    }
  }

  @Override
  public CoinMarketDataResult getHistoricalListing(List<String> coins, List<String> vsCurrencies,
      Long timestamp) {
    try {
      Response response = this.client.getHistoricalCoinQuotes(coins, vsCurrencies, timestamp);
      if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
        log.error(response.getResponseBody().get("error").asText());
        return new CoinMarketDataResult(this.providerEnum, ResultStatus.FAILURE,
            response.getResponseBody().get("error").asText(), null);
      }

      var ret = parser.parseGetCoinsHistoricalQuoteResult(response);
      ret.setProvider(this.providerEnum);
      if (ret.getStatus() == ResultStatus.SUCCESS &&
          ret.getCoinMarketDataDTOS().size() != coins.size()) {
        ret.setStatus(ResultStatus.PARTIAL_SUCCESS);
      }
      return ret;

    } catch (IOException e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(this.providerEnum, ResultStatus.FAILURE, e.getMessage(),
          null);
    }
  }
}
