package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class CoinMarketCapApiClient extends ApiClient {
  @Value("${coinmarketcap.path}")
  private String url;

  @Value("${coinmarketcap.key}")
  private String key;

  private Map<String, List<String>> headers;

  @PostConstruct
  void init() {
    this.headers = Map.of("X-CMC_PRO_API_KEY", new ArrayList<>(List.of(this.key)));
  }

  public Response getTopCoins(int take, int page, List<String> vsCurrencies) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("start", String.valueOf(1 + page * take));
    queryParams.put("limit", String.valueOf(take));
    queryParams.put("convert", String.join(",", vsCurrencies));
    String requestUrl =
        httpRequestBuilder.buildRequestURI(
            this.url + "/cryptocurrency/listings/latest", queryParams);
    return this.invokeGet(requestUrl, this.headers);
  }

  public Response getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline)
      throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("symbol", String.join(",", coins));
    queryParams.put("convert", String.join(",", vsCurrencies));
    String requestUrl =
        httpRequestBuilder.buildRequestURI(this.url + "/cryptocurrency/quotes/latest", queryParams);
    return this.invokeGet(requestUrl, this.headers);
  }

  public Response getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, long timestampFrom, long timestampTo)
      throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("symbol", String.join(",", coins));
    queryParams.put("convert", String.join(",", vsCurrencies));
    queryParams.put("time_start", String.valueOf(timestampTo));
    queryParams.put("time_end", String.valueOf(timestampFrom));
    queryParams.put("count", String.valueOf(100));
    queryParams.put("interval", "hourly");
    String requestUrl =
        httpRequestBuilder.buildRequestURI(
            this.url + "/cryptocurrency/quotes/historical", queryParams);
    return this.invokeGet(requestUrl, this.headers);
  }

  public Response getCoinInfo(List<String> coins) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("symbol", String.join(",", coins));
    String requestUrl =
        httpRequestBuilder.buildRequestURI(this.url + "/cryptocurrency/info", queryParams);
    return this.invokeGet(requestUrl, this.headers);
  }

  public Response getCoinsNames() throws IOException {
    String requestUrl = RequestBuilder.buildRequestURI(this.url + "/cryptocurrency/map");
    return this.invokeGet(requestUrl, this.headers);
  }
}
