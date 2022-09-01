package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
final class CoinGeckoApiClient extends ApiClient {
  @Value("${coingecko.path}")
  private String url;

  // TODO more than 1 currency
  public Response getTopCoins(int take, int page, List<String> vsCurrencies) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("page", String.valueOf(page));
    queryParams.put("per_page", String.valueOf(take));
    queryParams.put("vs_currency", vsCurrencies.get(0));
    String requestUrl = RequestBuilder.buildRequestURI(this.url + "/coins/markets", queryParams);
    return this.invokeGet(requestUrl);
  }

  public Response getCoinsNames() throws IOException {
    String requestUrl = RequestBuilder.buildRequestURI(this.url + "/coins/list");
    return this.invokeGet(requestUrl);
  }
}
