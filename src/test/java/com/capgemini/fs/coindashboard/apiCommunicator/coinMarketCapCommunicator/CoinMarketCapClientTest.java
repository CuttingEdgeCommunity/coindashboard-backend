package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CoinMarketCapClientTest extends CoinMarketCapTestBaseClass {

  private final Map<String, List<String>> headers =
      new HashMap<>() {
        {
          put(
              "X-CMC_PRO_API_KEY",
              new ArrayList<>() {
                {
                  add("dc8ad4b3-5f8a-4190-84e9-34efc1fd81a9");
                }
              });
        }
      };
  @Autowired private CoinMarketCapClient coinMarketCapClient;

  @MockBean private ApiClient apiClient;

  @BeforeEach
  public void setUp() throws IOException {
    String correctLatestUri =
        "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=btc%2Ceth&convert=usd%2Ceur";
    String correctHistoricalUri =
        String.format(
            "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/historical?symbol=btc%%2Ceth&convert=usd%%2Ceur&time_start=%s&count=1",
            this.timestamp);
    String errorLatestUri =
        "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?symbol=btc%2C+eth&convert=usd%2Ceur";
    String errorHistoricalUri =
        String.format(
            "https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/historical?symbol=btc%%2C+eth&convert=usd%%2Ceur&time_start=%s&count=1",
            this.timestamp);

    Mockito.when(apiClient.invokeGet(correctLatestUri, this.headers))
        .thenReturn(this.correctLatestR);
    Mockito.when(apiClient.invokeGet(correctHistoricalUri, this.headers))
        .thenReturn(this.correctHistoricalR);
    Mockito.when(apiClient.invokeGet(errorLatestUri, this.headers)).thenReturn(this.errorR);
    Mockito.when(apiClient.invokeGet(errorHistoricalUri, this.headers)).thenReturn(this.errorR);
  }

  @Test
  void getCoinQuotes() throws IOException {
    assertEquals(200, coinMarketCapClient.getCoinQuotes(this.coins, this.vsCurr).getResponseCode());
    assertEquals(
        400, coinMarketCapClient.getCoinQuotes(this.coinserr, this.vsCurr).getResponseCode());
  }

  @Test
  void getHistoricalCoinQuotes() throws IOException {
    assertEquals(
        200,
        coinMarketCapClient
            .getHistoricalCoinQuotes(this.coins, this.vsCurr, this.timestamp)
            .getResponseCode());
    assertEquals(
        400,
        coinMarketCapClient
            .getHistoricalCoinQuotes(this.coinserr, this.vsCurr, this.timestamp)
            .getResponseCode());
  }

  @TestConfiguration
  static class CoinMarketCapClientContextConfiguration {

    @Bean
    public CoinMarketCapClient coinMarketCapClient() {
      return new CoinMarketCapClient();
    }
  }
}
