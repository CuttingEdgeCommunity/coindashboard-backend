package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CoinMarketCapCommunicator implements ApiCommunicator {

  private final ApiProviderEnum providerEnum = ApiProviderEnum.COIN_MARKET_CAP;
  //  @Autowired
  private final ApiClient client = new ApiClient();
  //  @Autowired
  private final CoinMarketCapResponseParser parser = new CoinMarketCapResponseParser();
  private static final Logger log = LogManager.getLogger(CoinMarketCapCommunicator.class);

  private final String path = "https://sandbox-api.coinmarketcap.com";
  private final String version = "v2";
  private final String url = path + "/" + version; // TODO: move to env

  private final String key = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c";
  private final Map<String, List<String>> headers = new HashMap<>() {{
    put("X-CMC_PRO_API_KEY", new ArrayList<>() {{
      add(key);
    }});
  }};

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.providerEnum;
  }

  @Override
  public CoinMarketDataResult getCurrentListing(List<String> coins, List<String> vsCurrencies) {
    HashMap<String, String> queryParams = new HashMap<>() {{
      put("slug", String.join(",", coins));
      put("convert", String.join(",", vsCurrencies));
    }};
    String requestUrl = RequestBuilder.buildRequestURI(this.url + "/cryptocurrency/quotes/latest",
        queryParams);
    Response response;
    try {
      response = this.client.invokeGet(requestUrl, this.headers);
    } catch (IOException e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(ResultStatus.FAILURE, e.getMessage(), null);
    }
    if (response == null) {
      return new CoinMarketDataResult(ResultStatus.FAILURE, "unknown error", null);
    }
    if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
      return new CoinMarketDataResult(ResultStatus.FAILURE,
          response.getResponseBody().get("error").asText(), null);
    }

    CoinMarketDataResult coinMarketDataResult = new CoinMarketDataResult(ResultStatus.SUCCESS, null,
        null);
    try {
      return new CoinMarketDataResult(ResultStatus.SUCCESS, null,
          this.parser.parseGetCoinsQuoteResult(response.getResponseBody()));
    } catch (Exception e) {
      log.error(e.getMessage());
      return new CoinMarketDataResult(ResultStatus.FAILURE, e.getMessage(), new ArrayList<>());
    }
  }

  @Override
  public CoinMarketDataResult getHistoricalListing(List<String> coins, List<String> vsCurrencies,
      Long timestamp) {
    return null;
  }
}
