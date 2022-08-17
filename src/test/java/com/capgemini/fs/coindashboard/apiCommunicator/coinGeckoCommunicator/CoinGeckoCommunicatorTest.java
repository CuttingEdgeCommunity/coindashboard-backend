package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
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
class CoinGeckoCommunicatorTest extends CoinGeckoTestBaseClass {

  @TestConfiguration
  static class CoinGeckoComunicatorContextConfiguration {

    @Bean
    public CoinGeckoCommunicator coinGeckoCommunicator() {
      return new CoinGeckoCommunicator();
    }

    @Bean
    public CoinGeckoResponseParser coinGeckoResponseParser() {
      return new CoinGeckoResponseParser();
    }
  }

  @Autowired private CoinGeckoCommunicator coinGeckoCommunicator;

  @MockBean private CoinGeckoClient client;

  @BeforeEach
  public void setUp() throws IOException {
    Mockito.when(
            client.getCoinsMarkets(
                this.coins, this.vsCurr.get(0), "", "250", "", "", "1h,24h,7d,30d"))
        .thenReturn(this.correctLatestR);
    Mockito.when(
            client.getCoinsMarkets(
                this.coins, this.vsCurr.get(1), "", "250", "", "", "1h,24h,7d,30d"))
        .thenReturn(this.eurCorrectLatestR);
    Mockito.when(client.getMarketChart(this.coins.get(0), this.vsCurr.get(0), "1"))
        .thenReturn(this.btcCorrectHistoricalR);
    Mockito.when(client.getMarketChart(this.coins.get(0), this.vsCurr.get(1), "1"))
        .thenReturn(this.btcCorrectHistoricalR);
    Mockito.when(client.getMarketChart(this.coins.get(1), this.vsCurr.get(0), "1"))
        .thenReturn(this.ethCorrectHistoricalR);
    Mockito.when(client.getMarketChart(this.coins.get(1), this.vsCurr.get(1), "1"))
        .thenReturn(this.ethCorrectHistoricalR);
    Mockito.when(
            client.getCoinsMarkets(
                this.coinserr, this.vsCurr.get(0), "", "250", "", "", "1h,24h,7d,30d"))
        .thenReturn(this.errorR);
    Mockito.when(
            client.getCoinsMarkets(
                this.coinserr, this.vsCurr.get(1), "", "250", "", "", "1h,24h,7d,30d"))
        .thenReturn(this.errorR);
    Mockito.when(client.getMarketChart(this.coinserr.get(0), this.vsCurr.get(0), "1"))
        .thenReturn(this.errorHistoricalR);
    Mockito.when(client.getMarketChart(this.coinserr.get(1), this.vsCurr.get(0), "1"))
        .thenReturn(this.errorHistoricalR);
    Mockito.when(client.getMarketChart(this.coinserr.get(0), this.vsCurr.get(1), "1"))
        .thenReturn(this.errorHistoricalR);
    Mockito.when(client.getMarketChart(this.coinserr.get(1), this.vsCurr.get(1), "1"))
        .thenReturn(this.errorHistoricalR);
  }

  @Test
  void getProvider() {
    assertEquals(ApiProviderEnum.COIN_GECKO, coinGeckoCommunicator.getApiProvider());
  }

  @Test
  // @Disabled
  void getCurrentListing() {
    var correct = coinGeckoCommunicator.getCurrentListing(this.coins, this.vsCurr);
    // Collections.singletonList(this.vsCurr.get(0)));
    var error = coinGeckoCommunicator.getCurrentListing(this.coinserr, this.vsCurr);
    // Collections.singletonList(this.vsCurr.get(0)));
    assertEquals(ApiProviderEnum.COIN_GECKO, correct.getProvider(), "Ok1");
    assertEquals(ApiProviderEnum.COIN_GECKO, error.getProvider(), "Ok2");
    assertEquals(ResultStatus.SUCCESS, correct.getStatus(), "Ok3");
    assertEquals(ResultStatus.FAILURE, error.getStatus(), "Ok4");
  }

  @Test
  void getHistoricalListing() {
    var correct =
        coinGeckoCommunicator.getHistoricalListing(this.coins, this.vsCurr, this.timestamp);
    var error =
        coinGeckoCommunicator.getHistoricalListing(this.coinserr, this.vsCurr, this.timestamp);
    assertEquals(ApiProviderEnum.COIN_GECKO, correct.getProvider());
    assertEquals(ApiProviderEnum.COIN_GECKO, error.getProvider());
    assertEquals(ResultStatus.SUCCESS, correct.getStatus());
    assertEquals(ResultStatus.FAILURE, error.getStatus());
  }
}
