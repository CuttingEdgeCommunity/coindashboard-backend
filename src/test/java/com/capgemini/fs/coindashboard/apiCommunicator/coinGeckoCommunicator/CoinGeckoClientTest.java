package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import java.io.IOException;
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
class CoinGeckoClientTest extends CoinGeckoTestBaseClass {

  @Autowired private CoinGeckoClient coinGeckoClient;

  @MockBean private ApiClient apiClient;

  @BeforeEach
  public void setUp() throws IOException {
    String correctLatestUri =
        "https://api.coingecko.com/api/v3/coins/markets?ids=bitcoin%2Cethereum&vs_currency=usd&per_page=250&deltas=1h%2C24h%2C7d%2C30d";
    String correctHistoricalUri =
        "https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?vs_currency=usd&days=1";
    String errorLatestUri =
        "https://api.coingecko.com/api/v3/coins/markets?ids=eth%2C+btc&vs_currency=usd&per_page=250&deltas=1h%2C24h%2C7d%2C30d";
    String errorHistoricalUri =
        "https://api.coingecko.com/api/v3/coins/eth/market_chart?vs_currency=usd&days=1";

    Mockito.when(apiClient.invokeGet(correctLatestUri)).thenReturn(this.correctLatestR);
    Mockito.when(apiClient.invokeGet(correctHistoricalUri)).thenReturn(this.btcCorrectHistoricalR);
    Mockito.when(apiClient.invokeGet(errorLatestUri)).thenReturn(this.errorR);
    Mockito.when(apiClient.invokeGet(errorHistoricalUri)).thenReturn(this.errorR);
  }

  @Test
  void getCoinsMarkets() throws IOException {
    assertEquals(
        200,
        coinGeckoClient
            .getCoinsMarkets(
                this.coins, this.vsCurr.get(0), order, per_page, page, sparkline, deltas)
            .getResponseCode());
    assertEquals(
        400,
        coinGeckoClient
            .getCoinsMarkets(
                this.coinserr, this.vsCurr.get(0), order, per_page, page, sparkline, deltas)
            .getResponseCode());
  }

  @Test
  void getMarketChart() throws IOException {
    assertEquals(
        200,
        coinGeckoClient
            .getMarketChart(this.coins.get(0), this.vsCurr.get(0), "1")
            .getResponseCode());
    assertEquals(
        400,
        coinGeckoClient
            .getMarketChart(this.coinserr.get(0), this.vsCurr.get(0), "1")
            .getResponseCode());
  }

  @TestConfiguration
  static class CoinGeckoClientContextConfiguration {

    @Bean
    public CoinGeckoClient coinMarketCapClient() {
      return new CoinGeckoClient();
    }
  }
}
