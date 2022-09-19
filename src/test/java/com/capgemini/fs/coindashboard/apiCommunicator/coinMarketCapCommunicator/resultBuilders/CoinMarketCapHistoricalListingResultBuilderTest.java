package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapFieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData.CoinMarketCapHistoricalListingTestData;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData.CoinMarketCapTestDataBaseClass;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ApiCommunicatorMethodParametersDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoinMarketCapHistoricalListingResultBuilderTest {
  CoinMarketCapTestDataBaseClass data = new CoinMarketCapTestDataBaseClass();
  CoinMarketCapHistoricalListingTestData historicalListingTestData =
      new CoinMarketCapHistoricalListingTestData();
  CoinMarketCapHistoricalListingResultBuilder builder =
      new CoinMarketCapHistoricalListingResultBuilder();

  CoinMarketCapHistoricalListingResultBuilderTest() throws IOException {
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
    this.builder.setData(data.goodResponse, new ApiCommunicatorMethodParametersDto(List.of()));
    this.builder.setResultStatus();
    assertEquals(ResultStatus.SUCCESS, this.builder.getResult().getStatus());
  }

  @Test
  void setResultStatusError() {
    this.builder.getResult().setCoins(List.of());
    this.builder.setData(data.badResponse, new ApiCommunicatorMethodParametersDto());
    this.builder.setResultStatus();
    assertEquals(ResultStatus.FAILURE, this.builder.getResult().getStatus());
  }

  @Test
  void setResultStatusPartial() {
    this.builder.getResult().setCoins(List.of());
    this.builder.setData(data.goodResponse, new ApiCommunicatorMethodParametersDto(List.of("")));
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
    this.builder.setData(
        historicalListingTestData.btcEthResponse, new ApiCommunicatorMethodParametersDto());
    this.builder.setCoins();
    Result result = this.builder.getResult();
    assertEquals(2, result.getCoins().size());
    assertEquals(2, result.getCoins().get(0).getQuotes().size());
    assertEquals(3, result.getCoins().get(0).getQuotes().get("usd").getChart().size());
    assertEquals(
        0.4269687650365923,
        result.getCoins().get(0).getQuotes().get("usd").getChart().get(0).getPrice());
  }

  @Test
  void getMethod() {
    assertEquals(ApiCommunicatorMethodEnum.HISTORICAL_LISTING, this.builder.getMethod());
  }
}
