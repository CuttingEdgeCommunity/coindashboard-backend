package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
class CoinGeckoClient {

  @Value("${coingecko.path}")
  private String url;

  @Autowired private ApiClient client;

  public Response getCoinsMarkets(
      List<String> coins,
      String currency,
      String order,
      String per_page,
      String page,
      String sparkline,
      String deltas)
      throws IOException {
    LinkedHashMap<String, String> queryParams =
        new LinkedHashMap<>() {
          {
            put("ids", String.join(",", coins));
            put("vs_currency", currency);
            if (order != null) {
              if (!order.isBlank()) {
                put("order", order);
              }
            }
            if (per_page != null) {
              if (!per_page.isBlank()) {
                put("per_page", per_page);
              }
            }
            if (page != null) {
              if (!page.isBlank()) {
                put("page", page);
              }
            }
            if (sparkline != null) {
              if (!sparkline.isBlank()) {
                put("sparkline", sparkline);
              }
            }
            if (deltas != null) {
              if (!deltas.isBlank()) {
                put("price_change_percentage", deltas);
              }
            }
          }
        };

    String requestUrl = RequestBuilder.buildRequestURI(this.url + "/coins/markets", queryParams);
    return this.client.invokeGet(requestUrl);
  }

  public Response getMarketChart(String coin, String currency, String days) throws IOException {
    LinkedHashMap<String, String> queryParams =
        new LinkedHashMap<>() {
          {
            put("vs_currency", currency);
            put("days", days);
          }
        };

    String requestUrl =
        RequestBuilder.buildRequestURI(this.url + "/coins/" + coin + "/market_chart", queryParams);
    return this.client.invokeGet(requestUrl);
  }
}
