package com.capgemini.fs.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class CoinGeckoClientTest {
  @Test
  void invoke() throws IOException {
    var s = new ApiClient();
    var x = s.invoke(new RequestBuilder()
      .buildRequestURI("https://api.coingecko.com/api/v3/coins/markets",
        new ArrayList<>(), new LinkedHashMap<>(){{
        put("ids", "bitcoin,ethereum");
        put("vs_currency", "usd");
        put("price_change_percentage", "1h,24h,7d");
      }}));
  }
}