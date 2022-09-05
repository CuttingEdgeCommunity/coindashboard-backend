package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapFieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData.CoinMarketCapCoinInfoTestData;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData.CoinMarketCapTestDataBaseClass;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoinMarketCapCoinInfoResultBuilderTest {
  CoinMarketCapTestDataBaseClass data = new CoinMarketCapTestDataBaseClass();
  CoinMarketCapCoinInfoTestData infoTestData = new CoinMarketCapCoinInfoTestData();
  CoinMarketCapCoinInfoResultBuilder builder = new CoinMarketCapCoinInfoResultBuilder();

  CoinMarketCapCoinInfoResultBuilderTest() throws IOException {
    builder.mapper = new CoinMarketCapFieldNameMapper();
    builder.init();
  }

  @BeforeEach
  void initTest() {
    this.builder.reset();
  }

  @Test
  void parseStatus() {
    String noError = this.builder.parseStatus(data.goodStatusJson.get("status"));
    String error = this.builder.parseStatus(data.badStatusJson.get("status"));
    assertNull(noError);
    assertEquals("sample error", error);
  }

  @Test
  void setResultProvider() {
    this.builder.setResultProvider();
    assertEquals(ApiProviderEnum.COIN_MARKET_CAP, this.builder.getResult().getProvider());
  }

  @Test
  void setResultStatusNoError() {
    this.builder.getResult().setCoins(List.of());
    this.builder.setData(data.goodResponse, List.of());
    this.builder.setResultStatus();
    assertEquals(ResultStatus.SUCCESS, this.builder.getResult().getStatus());
  }

  @Test
  void setResultStatusError() {
    this.builder.getResult().setCoins(List.of());
    this.builder.setData(data.badResponse, List.of());
    this.builder.setResultStatus();
    assertEquals(ResultStatus.FAILURE, this.builder.getResult().getStatus());
  }

  @Test
  void setResultStatusPartial() {
    this.builder.getResult().setCoins(List.of());
    this.builder.setData(data.goodResponse, List.of(""));
    this.builder.setResultStatus();
    assertEquals(ResultStatus.PARTIAL_SUCCESS, this.builder.getResult().getStatus());
  }

  @Test
  void setErrorMessage() {
    this.builder.errorMessage = "sample";
    this.builder.setErrorMessage();
    assertEquals("sample", this.builder.getResult().getErrorMessage());
  }

  @Test
  void setCoins() {
    this.builder.setData(infoTestData.btcEthLeoResponse);
    this.builder.setCoins();
    Result result = this.builder.getResult();
    assertEquals(3, result.getCoins().size());
    assertEquals(
        "https://blockchain.coinmarketcap.com/chain/bitcoin",
        result.getCoins().get(0).getLinks().stream()
            .filter(link -> Objects.equals(link.getTitle(), "explorer"))
            .findFirst()
            .get()
            .getUrl());
    assertEquals(true, result.getCoins().get(2).getIs_token());
  }

  @Test
  void getMethod() {

    assertEquals(ApiCommunicatorMethodEnum.COIN_INFO, this.builder.getMethod());
  }
}
