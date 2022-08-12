package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
class CoinGeckoCommunicatorTest {

  @Autowired
  private CoinGeckoCommunicator s;

  @Test
  void getCurrentListing() {
    ArrayList<String> coins = new ArrayList<>();
    //coins.add("bitcoin");
    ArrayList<String> vsCurrencies = new ArrayList<>();
    vsCurrencies.add("usd");
    //vsCurrencies.add("eur");
    s.getCurrentListing(coins, vsCurrencies);
  }

  @Test
  void getHistoricalListing() {
    ArrayList<String> coins = new ArrayList<>();
    coins.add("bitcoin");
    ArrayList<String> vsCurrencies = new ArrayList<>();
    vsCurrencies.add("usd");
    s.getHistoricalListing(coins, vsCurrencies, 0L);
  }

}
