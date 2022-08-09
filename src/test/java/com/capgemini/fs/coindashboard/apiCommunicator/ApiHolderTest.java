package com.capgemini.fs.coindashboard.apiCommunicator;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.CoinGeckoCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapCommunicator;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ApiHolderTest {

  @MockBean public CoinMarketCapCommunicator coinMarketCapCommunicator;
  @MockBean public CoinGeckoCommunicator coinGeckoCommunicator;
  private ApiHolder apiHolder;

  @BeforeEach
  void setup() {
    Mockito.when(this.coinGeckoCommunicator.getApiProvider())
        .thenReturn(ApiProviderEnum.COIN_GECKO);
    Mockito.when(this.coinMarketCapCommunicator.getApiProvider())
        .thenReturn(ApiProviderEnum.COIN_MARKET_CAP);
    this.apiHolder =
        new ApiHolder(
            new HashSet<>(List.of(this.coinGeckoCommunicator, this.coinMarketCapCommunicator)));
  }

  @Test
  void getApiCommunicator() {
    assertSame(
        this.coinGeckoCommunicator, apiHolder.getApiCommunicator(ApiProviderEnum.COIN_GECKO));
    assertSame(
        this.coinMarketCapCommunicator,
        apiHolder.getApiCommunicator(ApiProviderEnum.COIN_MARKET_CAP));
  }

  @Test
  void getCoinMarketData() {}

  @Test
  void getHistoricalCoinMarketData() {}
}
