package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.CoinGeckoFieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ApiCommunicatorMethodParametersDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoinGeckoHistoricalListingResultBuilderTest {
  ObjectMapper mapper = new ObjectMapper();
  CoinGeckoHistoricalListingResultBuilder builder = new CoinGeckoHistoricalListingResultBuilder();
  JsonNode goodData;
  JsonNode badData;
  Response goodResponse;
  Response badResponse;

  CoinGeckoHistoricalListingResultBuilderTest() throws IOException {
    builder.mapper = new CoinGeckoFieldNameMapper();
    builder.init();
  }

  @BeforeEach
  void initTest() throws IOException {
    this.builder.reset();
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/HistoricalListingGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    goodData = mapper.readTree(targetStream);
    goodResponse = new Response(200, goodData);

    initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/BadResponse.txt");
    targetStream = new FileInputStream(initialFile);
    badData = mapper.readTree(targetStream);
    badResponse = new Response(400, badData);
  }

  @Test
  void parseStatus() {
    String noError = this.builder.parseStatus(goodData);
    String error = this.builder.parseStatus(badData);
    assertNull(noError);
    assertEquals("sample error", error);
  }

  @Test
  void setResultProvider() {
    this.builder.setResultProvider();
    assertEquals(ApiProviderEnum.COIN_GECKO, this.builder.getResult().getProvider());
  }

  @Test
  void setResultStatusNoError() {
    this.builder.getResult().setCoins(List.of());
    this.builder.setData(goodResponse, new ApiCommunicatorMethodParametersDto());
    this.builder.setResultStatus();
    assertEquals(ResultStatus.SUCCESS, this.builder.getResult().getStatus());
  }

  @Test
  void setResultStatusError() {
    this.builder.getResult().setCoins(List.of());
    this.builder.setData(badResponse, new ApiCommunicatorMethodParametersDto());
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
    this.builder.setData(
        goodResponse,
        new ApiCommunicatorMethodParametersDto(List.of("bitcoin"), List.of("usd"), 0L, 0L));
    this.builder.setCoins();
    Result result = this.builder.getResult();
    assertEquals(1, result.getCoins().size());
    assertEquals(1, result.getCoins().get(0).getQuotes().size());
    assertEquals(4, result.getCoins().get(0).getQuotes().get("usd").getChart().size());
    assertEquals(
        271.2882000000001,
        result.getCoins().get(0).getQuotes().get("usd").getChart().get(0).getPrice());
  }

  @Test
  void getMethod() {
    assertEquals(ApiCommunicatorMethodEnum.HISTORICAL_LISTING, this.builder.getMethod());
  }
}
