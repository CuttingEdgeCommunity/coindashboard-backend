package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapFieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData.CoinMarketCapTestDataBaseClass;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData.CoinMarketCapTopCoinsTestData;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoinMarketCapTopCoinsResultBuilderTest {
  CoinMarketCapTestDataBaseClass data = new CoinMarketCapTestDataBaseClass();
  CoinMarketCapTopCoinsTestData topCoinsTestData = new CoinMarketCapTopCoinsTestData();
  CoinMarketCapTopCoinsResultBuilder builder = new CoinMarketCapTopCoinsResultBuilder();

  CoinMarketCapTopCoinsResultBuilderTest() throws IOException {
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
  void setErrorMessage() {
    this.builder.errorMessage = "sample";
    this.builder.setErrorMessage();
    assertEquals("sample", this.builder.getResult().getErrorMessage());
  }

  @Test
  void setCoins() {
    this.builder.setData(topCoinsTestData.top3Response);
    this.builder.setCoins();
    Result result = this.builder.getResult();
    assertEquals(3, result.getCoins().size());
    assertEquals(
        1.93891399,
        result.getCoins().get(1).getQuotes().get("usd").getCurrentQuote().getDeltas().stream()
            .filter(delta -> Objects.equals(delta.getInterval(), IntervalEnum.ONE_DAY.name()))
            .findFirst()
            .get()
            .getPct());
    assertEquals(3, result.getCoins().get(2).getMarketCapRank());
  }

  @Test
  void getMethod() {

    assertEquals(ApiCommunicatorMethodEnum.TOP_COINS, this.builder.getMethod());
  }
}
