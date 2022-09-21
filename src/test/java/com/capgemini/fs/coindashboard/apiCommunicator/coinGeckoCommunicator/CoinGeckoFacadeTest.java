package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders.CoinGeckoCoinInfoResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders.CoinGeckoCurrentListingResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders.CoinGeckoHistoricalListingResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders.CoinGeckoTopCoinsResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilderDirector;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.HttpRequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {
      CoinGeckoFacade.class,
      CoinGeckoTranslator.class,
      CoinGeckoTopCoinsResultBuilder.class,
      CoinGeckoHistoricalListingResultBuilder.class,
      CoinGeckoCurrentListingResultBuilder.class,
      CoinGeckoCoinInfoResultBuilder.class,
      HttpRequestBuilder.class,
      CoinGeckoFieldNameMapper.class,
      ResultBuilderDirector.class
    })
class CoinGeckoFacadeTest {
  @Autowired private CoinGeckoFacade facade;
  @MockBean private CoinGeckoApiClient client;
  @MockBean private CoinGeckoTranslator translator;
  private ObjectMapper mapper = new ObjectMapper();
  Response badResponse;

  @BeforeEach
  void setup() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/BadResponse.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    badResponse = new Response(400, data);
    Mockito.when(client.getTopCoins(3, 1, "aed")).thenReturn(badResponse);
    Mockito.when(client.getCoinInfo("ethereum")).thenReturn(badResponse);
    Mockito.when(translator.translate(List.of("bitcoin"), ApiCommunicatorMethodEnum.COIN_INFO))
        .thenReturn(List.of("bitcoin"));
    Mockito.when(
            translator.translate(
                List.of("bitcoin", "ethereum"), ApiCommunicatorMethodEnum.COIN_INFO))
        .thenReturn(List.of("bitcoin", "ethereum"));

    Mockito.when(
            translator.translate(List.of("bitcoin"), ApiCommunicatorMethodEnum.CURRENT_LISTING))
        .thenReturn(List.of("bitcoin"));
    Mockito.when(
            translator.translate(
                List.of("bitcoin", "ethereum"), ApiCommunicatorMethodEnum.CURRENT_LISTING))
        .thenReturn(List.of("bitcoin", "ethereum"));

    Mockito.when(
            translator.translate(List.of("bitcoin"), ApiCommunicatorMethodEnum.HISTORICAL_LISTING))
        .thenReturn(List.of("bitcoin"));
    Mockito.when(client.getCurrentListing("ethereum", true)).thenReturn(badResponse);
    Mockito.when(client.getHistoricalListing("bitcoin", "aed", 0L, 0L)).thenReturn(badResponse);
  }

  @Test
  void getApiProviderTest() {
    assertEquals(ApiProviderEnum.COIN_GECKO, facade.getApiProvider());
  }

  @Test
  void getTopCoinsPartialSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/TopCoinsGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getTopCoins(3, 1, "usd")).thenReturn(response);

    Optional<Result> result = facade.getTopCoins(3, 1, List.of("usd", "aed"));
    assertEquals(ResultStatus.PARTIAL_SUCCESS, result.get().getStatus());
    assertEquals(3, result.get().getCoins().size());
  }

  @Test
  void getTopCoinsSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/TopCoinsGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getTopCoins(3, 1, "usd")).thenReturn(response);

    Optional<Result> result = facade.getTopCoins(3, 1, List.of("usd", "usd"));
    assertEquals(ResultStatus.SUCCESS, result.get().getStatus());
    assertEquals(3, result.get().getCoins().size());
  }

  @Test
  void getCoinInfoPartialSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/CoinInfoGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getCoinInfo("bitcoin")).thenReturn(response);

    Optional<Result> result = facade.getCoinInfo(List.of("bitcoin", "ethereum"));
    assertEquals(ResultStatus.SUCCESS, result.get().getStatus());
    assertEquals("bitcoin", result.get().getCoins().get(0).getName());
  }

  @Test
  void getCoinInfoSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/CoinInfoGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getCoinInfo("bitcoin")).thenReturn(response);

    Optional<Result> result = facade.getCoinInfo(List.of("bitcoin"));
    assertEquals(ResultStatus.FAILURE, result.get().getStatus());
  }

  @Test
  void getCurrentListingPartialSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/CurrentListingGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getCurrentListing("bitcoin", true)).thenReturn(response);

    Optional<Result> result =
        facade.getCurrentListing(List.of("bitcoin", "ethereum"), List.of("usd"), true);
    assertEquals(ResultStatus.PARTIAL_SUCCESS, result.get().getStatus());
    assertEquals(1, result.get().getCoins().get(0).getMarketCapRank());
  }

  @Test
  void getCurrentListingSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/CurrentListingGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getCurrentListing("bitcoin", true)).thenReturn(response);

    Optional<Result> result = facade.getCurrentListing(List.of("bitcoin"), List.of("usd"), true);
    assertEquals(ResultStatus.SUCCESS, result.get().getStatus());
    assertEquals(1, result.get().getCoins().get(0).getMarketCapRank());
  }

  @Test
  void getHistoricalListingPartialSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/HistoricalListingGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getHistoricalListing("bitcoin", "usd", 0L, 0L)).thenReturn(response);

    Optional<Result> result =
        facade.getHistoricalListing(List.of("bitcoin"), List.of("usd", "aed"), 0L, 0L);
    assertEquals(ResultStatus.PARTIAL_SUCCESS, result.get().getStatus());
    assertEquals(4, result.get().getCoins().get(0).getQuotes().get("usd").getChart().size());
  }

  @Test
  void getHistoricalListingSuccessTest() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinGeckoCommunicator/resultBuilders/testData/HistoricalListingGood.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    JsonNode data = mapper.readTree(targetStream);
    Response response = new Response(200, data);
    Mockito.when(client.getHistoricalListing("bitcoin", "usd", 0L, 0L)).thenReturn(response);

    Optional<Result> result =
        facade.getHistoricalListing(List.of("bitcoin"), List.of("usd"), 0L, 0L);
    assertEquals(ResultStatus.SUCCESS, result.get().getStatus());
    assertEquals(4, result.get().getCoins().get(0).getQuotes().get("usd").getChart().size());
  }
}
