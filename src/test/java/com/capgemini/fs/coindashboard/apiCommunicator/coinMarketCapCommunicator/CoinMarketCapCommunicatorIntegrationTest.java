package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(
    classes = {
      CoinMarketCapCommunicator.class,
      CoinMarketCapClient.class,
      CoinMarketCapResponseParser.class,
      ApiClient.class
    })
public class CoinMarketCapCommunicatorIntegrationTest {

  @Autowired private CoinMarketCapCommunicator coinMarketCapCommunicator;

  @Test
  @Disabled
  void a() { // TODO: figure out how to exclude this test from default runner
    //    var x = coinMarketCapCommunicator.getCurrentListing(new ArrayList<>(){{add("btc");
    // add("eth");}}, new ArrayList<>(){{add("usd");}});
    var y = 2;
  }
}
