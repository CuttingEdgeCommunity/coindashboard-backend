package com.capgemini.fs.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.apiCommunicator.utils.RequestBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CoinGeckoClientTest {
  @Test
  void invoke() throws IOException {
    var s = new CoinGeckoClient();
    var x = s.invoke(new RequestBuilder()
      .buildRequestURI("https://api.coingecko.com/api/v3/coins/markets",
        new ArrayList<>(), new LinkedHashMap<>(){{
        put("ids", "bitcoin,ethereum");
        put("vs_currency", "usd");
        put("price_change_percentage", "1h,24h,7d");
      }}));
  }
}