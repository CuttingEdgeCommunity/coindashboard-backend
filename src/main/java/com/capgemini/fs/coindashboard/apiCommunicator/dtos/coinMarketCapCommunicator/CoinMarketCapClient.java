package com.capgemini.fs.coindashboard.apiCommunicator.dtos.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CoinMarketCapClient {

  @Value("${coinmarketcap.path}")
  private String url;

  @Value("${coinmarketcap.key}")
  private String key;

  private Map<String, List<String>> headers;

  @Autowired private ApiClient client;

  @PostConstruct
  void init() {
    this.headers = new HashMap<>(Map.of("X-CMC_PRO_API_KEY", new ArrayList<>(List.of(this.key))));
  }

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
