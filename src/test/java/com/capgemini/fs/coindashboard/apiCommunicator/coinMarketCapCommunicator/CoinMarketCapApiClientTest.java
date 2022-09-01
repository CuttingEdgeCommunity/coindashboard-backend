package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CoinMarketCapApiClient.class})
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinMarketCapApiClientTest {

  @Autowired private CoinMarketCapApiClient coinMarketCapApiClient;

  @Test
  void getCoinsNames() throws IOException {
    assertEquals(200, coinMarketCapApiClient.getCoinsNames().getResponseCode());
  }
}
