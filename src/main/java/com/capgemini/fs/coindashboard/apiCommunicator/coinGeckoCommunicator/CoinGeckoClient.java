package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

@Component
class CoinGeckoClient {

  private static final Logger log = LogManager.getLogger(CoinGeckoClient.class);
  private final String path = "https://api.coingecko.com/api/";
  private final String version = "v3";
  private final String url = path + version; // TODO: move to env
  @Autowired private ApiClient client;
  public Response getCoinsMarkets(List<String> coins, String currency, String order, String per_page, String page, String sparkline, String deltas) throws IOException {
    LinkedHashMap<String, String> queryParams =
        new LinkedHashMap<>() {
          {
            put("ids", String.join(",", coins));
            put("vs_currency", currency);            
            if (order!=null){
              if (!order.isBlank()){
                put("order", order);
              }              
            }
            if (per_page!=null){
              if (!per_page.isBlank()){
                put("per_page", per_page);
              }
            }
            if (page!=null){
              if (!page.isBlank()){
                put("page", page);
              }
            }
            if (sparkline!=null){
              if (!sparkline.isBlank()){
                put("sparkline", sparkline);
              }
            }
            if (deltas!=null){
              if (!deltas.isBlank()){
                put("deltas", deltas);
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
        RequestBuilder.buildRequestURI(this.url + "/coins/" + coin + "/market_chart",queryParams);
    return this.client.invokeGet(requestUrl);
  }
}
