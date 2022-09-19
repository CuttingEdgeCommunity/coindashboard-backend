package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
final class CoinGeckoApiClient extends ApiClient {
  @Value("${coingecko.path}")
  private String url;

  public Response getTopCoins(int take, int page, String vsCurrency) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("page", String.valueOf(page + 1));
    queryParams.put("per_page", String.valueOf(take));
    queryParams.put("vs_currency", vsCurrency);
    queryParams.put("price_change_percentage", "1h,24h,7d");
    String requestUrl =
        httpRequestBuilder.buildRequestURI(this.url + "/coins/markets", queryParams);
    return this.invokeGet(requestUrl);
  }

  public Response getCoinInfo(String coin) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    String requestUrl =
        httpRequestBuilder.buildRequestURI(
            this.url + String.format("/coins/%s", coin), queryParams);
    return this.invokeGet(requestUrl);
  }

  public Response getCurrentListing(String coin, boolean include7dSparkline) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    String requestUrl =
        httpRequestBuilder.buildRequestURI(
            this.url + String.format("/coins/%s", coin), queryParams);
    return this.invokeGet(requestUrl);
  }

  public Response getHistoricalListing(
      String coin, String vsCurrency, long timestampFrom, long timestampTo) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("vs_currency", vsCurrency);
    queryParams.put("to", String.valueOf(timestampTo));
    queryParams.put("from", String.valueOf(timestampFrom));
    String requestUrl =
        httpRequestBuilder.buildRequestURI(
            this.url + String.format("/coins/%s/market_chart/range", coin), queryParams);
    return this.invokeGet(requestUrl);
  }

  public Response getCoinsNames() throws IOException {
    String requestUrl = httpRequestBuilder.buildRequestURI(this.url + "/coins/list");
    return this.invokeGet(requestUrl);
  }
}
