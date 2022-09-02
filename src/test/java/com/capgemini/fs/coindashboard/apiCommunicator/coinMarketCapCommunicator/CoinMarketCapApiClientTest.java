package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

@Disabled
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinMarketCapApiClientTest {
  @Mock(answer = Answers.CALLS_REAL_METHODS)
  CoinMarketCapApiClient coinMarketCapApiClient;

  @Value("${coinmarketcap.path}")
  String url;

  @BeforeEach
  void initialize() throws IOException {
    Mockito.when(
            coinMarketCapApiClient.invokeGet(eq(url + "/cryptocurrency/listings/latest"), anyMap()))
        .thenReturn(new Response(200, null));
  }

  @Test
  void init() {}

  @Test
  void getTopCoins() {}

  @Test
  void getCurrentListing() {}

  @Test
  void getHistoricalListing() {}

  @Test
  void getCoinInfo() {}
}
