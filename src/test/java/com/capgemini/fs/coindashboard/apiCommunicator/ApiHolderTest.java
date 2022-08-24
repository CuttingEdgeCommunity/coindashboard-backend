package com.capgemini.fs.coindashboard.apiCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.CoinGeckoCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.coinMarketCapCommunicator.CoinMarketCapCommunicator;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import java.util.ArrayList;
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

  private final List<String> coins = new ArrayList<>(List.of("btc"));
  private final List<String> vs = new ArrayList<>(List.of("usd"));

  @BeforeEach
  void setup() {
    Mockito.when(this.coinGeckoCommunicator.getApiProvider())
        .thenReturn(ApiProviderEnum.COIN_GECKO);
    Mockito.when(this.coinMarketCapCommunicator.getApiProvider())
        .thenReturn(ApiProviderEnum.COIN_MARKET_CAP);

    var resErr = new CoinMarketDataResult();
    resErr.setStatus(ResultStatus.FAILURE);
    resErr.setProvider(ApiProviderEnum.COIN_GECKO);
    var resSucc = new CoinMarketDataResult();
    resSucc.setStatus(ResultStatus.SUCCESS);
    resSucc.setProvider(ApiProviderEnum.COIN_MARKET_CAP);

    Mockito.when(this.coinGeckoCommunicator.getCurrentListing(Mockito.anyList(), Mockito.anyList()))
        .thenReturn(resErr);
    Mockito.when(
            this.coinMarketCapCommunicator.getCurrentListing(Mockito.anyList(), Mockito.anyList()))
        .thenReturn(resErr);
    Mockito.when(this.coinMarketCapCommunicator.getCurrentListing(this.coins, this.vs))
        .thenReturn(resSucc);

    Mockito.when(
            this.coinGeckoCommunicator.getHistoricalListing(
                Mockito.anyList(), Mockito.anyList(), Mockito.anyLong()))
        .thenReturn(resErr);
    Mockito.when(
            this.coinMarketCapCommunicator.getHistoricalListing(
                Mockito.anyList(), Mockito.anyList(), Mockito.anyLong()))
        .thenReturn(resErr);
    Mockito.when(this.coinMarketCapCommunicator.getHistoricalListing(this.coins, this.vs, 0L))
        .thenReturn(resSucc);

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
  void getCoinMarketData() {
    assertEquals(
        ApiProviderEnum.COIN_MARKET_CAP, this.apiHolder.getCoinMarketData("btc").getProvider());
    assertNull(this.apiHolder.getCoinMarketData("eth"));
  }

  @Test
  void getHistoricalCoinMarketData() {
    assertEquals(
        ApiProviderEnum.COIN_MARKET_CAP,
        this.apiHolder.getHistoricalCoinMarketData("btc", 0L).getProvider());
    assertNull(this.apiHolder.getHistoricalCoinMarketData("eth", 0L));
  }
}
