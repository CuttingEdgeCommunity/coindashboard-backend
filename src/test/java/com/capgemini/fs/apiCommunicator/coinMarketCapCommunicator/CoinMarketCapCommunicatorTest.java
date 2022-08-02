package com.capgemini.fs.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapCommunicator;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CoinMarketCapCommunicatorTest {

  @Autowired
  CoinMarketCapCommunicator coinMarketCapCommunicator;

  @Test
  void getCurrentListing() {
    coinMarketCapCommunicator.getCurrentListing(
        new ArrayList<>() {{
          add("bitcoin");
        }},
        new ArrayList<>() {{
          add("usd");
        }});
    System.out.println();
  }
}