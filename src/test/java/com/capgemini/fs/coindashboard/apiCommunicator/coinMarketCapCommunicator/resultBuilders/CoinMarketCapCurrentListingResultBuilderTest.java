package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapFieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData.CoinMarketCapCurrentListingTestData;
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

public class CoinMarketCapCurrentListingResultBuilderTest {
  CoinMarketCapTestDataBaseClass data = new CoinMarketCapTestDataBaseClass();
  CoinMarketCapCurrentListingTestData currentListingTestData =
      new CoinMarketCapCurrentListingTestData();
  CoinMarketCapCurrentListingResultBuilder builder = new CoinMarketCapCurrentListingResultBuilder();

  CoinMarketCapCurrentListingResultBuilderTest() throws IOException {
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
    this.builder.setData(currentListingTestData.btcEthLeoResponse);
    this.builder.setCoins();
    Result result = this.builder.getResult();
    assertEquals(3, result.getCoins().size());
    assertEquals(2, result.getCoins().get(0).getQuotes().size());
    assertEquals(
        1.18844806,
        result.getCoins().get(1).getQuotes().get("usd").getCurrentQuote().getDeltas().stream()
            .filter(delta -> Objects.equals(delta.getInterval(), IntervalEnum.ONE_DAY.name()))
            .findFirst()
            .get()
            .getPct());
    assertEquals(
        5117317292.315106,
        result.getCoins().get(2).getQuotes().get("usd").getCurrentQuote().getMarket_cap());
  }

  @Test
  void getMethod() {
    assertEquals(ApiCommunicatorMethodEnum.CURRENT_LISTING, this.builder.getMethod());
  }
}
