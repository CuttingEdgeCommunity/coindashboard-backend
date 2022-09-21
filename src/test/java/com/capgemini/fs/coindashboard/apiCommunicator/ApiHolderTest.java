package com.capgemini.fs.coindashboard.apiCommunicator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.CoinGeckoFacade;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapFacade;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ApiHolderTest {
  @MockBean public CoinMarketCapFacade coinMarketCapCommunicator;
  @MockBean public CoinGeckoFacade coinGeckoCommunicator;
  private ApiHolder apiHolder;

  int cgCalls, cmcCalls = 0;

  @BeforeEach
  void mockCMCFacade() {
    Answer<Result> resSuccess =
        new Answer<Result>() {
          @Override
          public Result answer(InvocationOnMock invocationOnMock) throws Throwable {
            cmcCalls++;
            return new Result(ApiProviderEnum.COIN_MARKET_CAP, ResultStatus.SUCCESS, ",", null);
          }
        };
    this.cmcCalls = 0;
    Mockito.when(this.coinMarketCapCommunicator.getApiProvider())
        .thenReturn(ApiProviderEnum.COIN_MARKET_CAP);
    Mockito.when(
            this.coinMarketCapCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.CURRENT_LISTING), ArgumentMatchers.<Object>any()))
        .thenAnswer(resSuccess);
    Mockito.when(
            this.coinMarketCapCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.HISTORICAL_LISTING), ArgumentMatchers.<Object>any()))
        .thenAnswer(resSuccess);
    Mockito.when(
            this.coinMarketCapCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.TOP_COINS), ArgumentMatchers.<Object>any()))
        .thenAnswer(resSuccess);
    Mockito.when(
            this.coinMarketCapCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.COIN_INFO), ArgumentMatchers.<Object>any()))
        .thenAnswer(resSuccess);
  }

  @BeforeEach
  void mockCGFacade() {
    this.cgCalls = 0;
    Answer<Result> res =
        new Answer<Result>() {
          @Override
          public Result answer(InvocationOnMock invocationOnMock) throws Throwable {
            cgCalls++;
            return new Result(ApiProviderEnum.COIN_GECKO, ResultStatus.SUCCESS, ",", null);
          }
        };
    Answer<Result> resError =
        new Answer<Result>() {
          @Override
          public Result answer(InvocationOnMock invocationOnMock) throws Throwable {
            cgCalls++;
            return new Result(ApiProviderEnum.COIN_GECKO, ResultStatus.FAILURE, ",", null);
          }
        };
    Mockito.when(this.coinGeckoCommunicator.getApiProvider())
        .thenReturn(ApiProviderEnum.COIN_GECKO);
    Mockito.when(
            this.coinGeckoCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.CURRENT_LISTING), ArgumentMatchers.<Object>any()))
        .thenAnswer(res);
    Mockito.when(
            this.coinGeckoCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.HISTORICAL_LISTING), ArgumentMatchers.<Object>any()))
        .thenAnswer(res);
    Mockito.when(
            this.coinGeckoCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.TOP_COINS), ArgumentMatchers.<Object>any()))
        .thenAnswer(res);
    Mockito.when(
            this.coinGeckoCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.COIN_INFO), ArgumentMatchers.<Object>any()))
        .thenAnswer(res);

    Mockito.when(
            this.coinGeckoCommunicator.executeMethod(
                eq(ApiCommunicatorMethodEnum.COIN_INFO), eq(List.of("bttc"))))
        .thenAnswer(resError);
  }

  @BeforeEach
  void initApiHolder() {
    this.apiHolder =
        new ApiHolder(
            new HashSet<>(List.of(this.coinGeckoCommunicator, this.coinMarketCapCommunicator)));
  }

  @Test
  void getApiCommunicator() {
    assertSame(
        this.coinMarketCapCommunicator,
        this.apiHolder.getApiCommunicator(ApiProviderEnum.COIN_MARKET_CAP));
    assertSame(
        this.coinGeckoCommunicator, this.apiHolder.getApiCommunicator(ApiProviderEnum.COIN_GECKO));
  }

  @Test
  void getCurrentListing() {
    this.apiHolder.getCurrentListing(new ArrayList<>(), new ArrayList<>(), false);
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void getCurrentListingFiltered() {
    this.apiHolder.getCurrentListing(
        List.of(ApiProviderEnum.COIN_MARKET_CAP), new ArrayList<>(), new ArrayList<>(), false);
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void getHistoricalListing() {
    this.apiHolder.getHistoricalListing(new ArrayList<>(), new ArrayList<>(), 0L, 0L);
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void getHistoricalListingFiltered() {
    this.apiHolder.getHistoricalListing(
        List.of(ApiProviderEnum.COIN_MARKET_CAP), new ArrayList<>(), new ArrayList<>(), 0L, 0L);
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void getTopCoins() {
    this.apiHolder.getTopCoins(0, 0, new ArrayList<>());
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void getTopCoinsFiltered() {
    this.apiHolder.getTopCoins(List.of(ApiProviderEnum.COIN_MARKET_CAP), 0, 0, new ArrayList<>());
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void getCoinInfo() {
    this.apiHolder.getCoinInfo(new ArrayList<>());
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void getCoinInfoFiltered() {
    this.apiHolder.getCoinInfo(List.of(ApiProviderEnum.COIN_MARKET_CAP), new ArrayList<>());
    assertEquals(0, this.cgCalls);
    assertEquals(1, this.cmcCalls);
  }

  @Test
  void executeAllFailures() {
    this.apiHolder.getCoinInfo(List.of(ApiProviderEnum.COIN_GECKO), List.of("bttc"));
    assertEquals(1, this.cgCalls);
  }
}
