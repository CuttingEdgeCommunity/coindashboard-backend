package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
final class CoinGeckoApiClient extends ApiClient {
  @Value("${coingecko.path}")
  private String url;

  public Response getTopCoins(int take, int page, String vsCurrency) throws IOException {
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("page", String.valueOf(page));
    queryParams.put("per_page", String.valueOf(take));
    queryParams.put("vs_currency", vsCurrency);
    queryParams.put("price_change_percentage", "1h,24h,7d");
    String requestUrl =
        RequestBuilder.buildRequestURI(this.url + "/coins/markets", queryParams);
    return this.invokeGet(requestUrl);
  }
}
