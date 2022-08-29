package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
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
final class CoinMarketCapApiClient extends ApiClient {
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
        RequestBuilder.buildRequestURI(this.url + "/cryptocurrency/listings/latest", queryParams);
    return this.invokeGet(requestUrl, this.headers);
  }
}
