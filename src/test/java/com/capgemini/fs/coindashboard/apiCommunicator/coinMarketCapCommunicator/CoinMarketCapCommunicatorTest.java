package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
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
class CoinMarketCapCommunicatorTest extends CoinMarketCapTestBaseClass {

  @TestConfiguration
  static class CoinMarketCapCommunicatorContextConfiguration {

    @Bean
    public CoinMarketCapCommunicator coinMarketCapCommunicator() {
      return new CoinMarketCapCommunicator();
    }

    @Bean
    public CoinMarketCapResponseParser coinMarketCapParser() {
      return new CoinMarketCapResponseParser();
    }
  }

  @Autowired
  private CoinMarketCapCommunicator coinMarketCapCommunicator;

//  @MockBean
//  private ApiClient apiClient;
@MockBean
private CoinMarketCapClient client;

  @BeforeEach
  public void setUp() throws IOException {
    Mockito.when(client.getCoinQuotes(this.coins, this.vsCurr))
        .thenReturn(this.correctLatestR);
    Mockito.when(client.getHistoricalCoinQuotes(this.coins, this.vsCurr, this.timestamp))
        .thenReturn(this.correctHistoricalR);
    Mockito.when(client.getCoinQuotes(this.coinserr, this.vsCurr))
        .thenReturn(this.errorR);
    Mockito.when(client.getHistoricalCoinQuotes(this.coinserr, this.vsCurr, this.timestamp))
        .thenReturn(this.errorR);
  }

  @Test
  void getProvider() {
    assertEquals(ApiProviderEnum.COIN_MARKET_CAP, coinMarketCapCommunicator.getApiProvider());
  }

  @Test
  void getCurrentListing() {
    var correct = coinMarketCapCommunicator.getCurrentListing(this.coins, this.vsCurr);
    var error = coinMarketCapCommunicator.getCurrentListing(this.coinserr, this.vsCurr);
    assertEquals(ApiProviderEnum.COIN_MARKET_CAP, correct.getProvider());
    assertEquals(ApiProviderEnum.COIN_MARKET_CAP, error.getProvider());
    assertEquals(ResultStatus.SUCCESS, correct.getStatus());
    assertEquals(ResultStatus.FAILURE, error.getStatus());
  }

  @Test
  void getHistoricalListing() {
    var correct = coinMarketCapCommunicator.getHistoricalListing(this.coins, this.vsCurr,
        this.timestamp);
    var error = coinMarketCapCommunicator.getHistoricalListing(this.coinserr, this.vsCurr,
        this.timestamp);
    assertEquals(ApiProviderEnum.COIN_MARKET_CAP, correct.getProvider());
    assertEquals(ApiProviderEnum.COIN_MARKET_CAP, error.getProvider());
    assertEquals(ResultStatus.SUCCESS, correct.getStatus());
    assertEquals(ResultStatus.FAILURE, error.getStatus());
  }
}