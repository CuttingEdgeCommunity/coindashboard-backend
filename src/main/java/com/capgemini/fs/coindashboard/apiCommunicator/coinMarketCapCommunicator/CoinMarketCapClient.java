package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapClient {

  private static final Logger log = LogManager.getLogger(CoinMarketCapClient.class);
  private final String path = "https://pro-api.coinmarketcap.com";
  private final String version = "v2";
  private final String url = path + "/" + version; // TODO: move to env
  private final String key = "dc8ad4b3-5f8a-4190-84e9-34efc1fd81a9";
  private final Map<String, List<String>> headers =
      new HashMap<>() {
        {
          put(
              "X-CMC_PRO_API_KEY",
              new ArrayList<>() {
                {
                  add(key);
                }
              });
        }
      };
  @Autowired private ApiClient client;

  public Response getCoinQuotes(List<String> coins, List<String> vsCurrencies) throws IOException {
    LinkedHashMap<String, String> queryParams =
        new LinkedHashMap<>() {
          {
            put("symbol", String.join(",", coins));
            put("convert", String.join(",", vsCurrencies));
          }
        };

    String requestUrl =
        RequestBuilder.buildRequestURI(this.url + "/cryptocurrency/quotes/latest", queryParams);
    return this.client.invokeGet(requestUrl, this.headers);
  }

  public Response getHistoricalCoinQuotes(
      List<String> coins, List<String> vsCurrencies, long timestamp) throws IOException {
    LinkedHashMap<String, String> queryParams =
        new LinkedHashMap<>() {
          {
            put("symbol", String.join(",", coins));
            put("convert", String.join(",", vsCurrencies));
            put("time_start", Long.toString(timestamp));
            put("count", "1");
          }
        };

    String requestUrl =
        RequestBuilder.buildRequestURI(this.url + "/cryptocurrency/quotes/historical", queryParams);
    return this.client.invokeGet(requestUrl, this.headers);
  }
}
