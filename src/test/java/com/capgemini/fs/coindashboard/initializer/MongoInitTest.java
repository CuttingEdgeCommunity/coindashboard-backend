package com.capgemini.fs.coindashboard.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.mongodb.lang.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
@ExtendWith({SpringExtension.class, MockitoExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(classes = {MongoInit.class})
@Log4j2
public class MongoInitTest {
  @MockBean MongoTemplate mongoTemplate;
  @MockBean private CreateQueries createQueries;
  @MockBean private GetQueries getQueries;
  @MockBean private ApiHolder apiHolder;
  @Autowired private MongoInit mongoInit;
  private Coin coin;
  private final List<Coin> _coins = new ArrayList<Coin>();
  private final List<Coin> coins = new ArrayList<>();
  private final String vs_currency = "usd";
  private final List<String> vsCurrencies = new ArrayList<>();
  private final Map<String, Quote> quotes = new HashMap<>();
  private final Quote quote = new Quote(vs_currency, null, null);
  private final List<Link> links = new ArrayList<>();
  private Optional<Result> resultOfGetTopCoins = Optional.empty();
  private Result resultOfGetCoinInfo;

  @BeforeEach
  void setup() {
    quotes.put(vs_currency, quote);
    vsCurrencies.add(vs_currency);
    links.add(new Link("website", "https://bitcoin.org"));
    coin =
        new Coin(
            "1234",
            "BLABLA",
            "btc",
            1,
            "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
            123L,
            false,
            "dafdfadffs",
            links,
            "sfjdfsdfjk",
            quotes);
    for (int i = 0; i < 1000; i++) coins.add(coin);
    resultOfGetCoinInfo =
        new Result(
            ApiProviderEnum.COIN_MARKET_CAP,
            ResultStatus.SUCCESS,
            "dfadfkajdlkfjalkdjfklakflkadfkla",
            coins);
  }

  @Test
  void afterPropertiesSet_success(CapturedOutput output) {
    mongoInit.afterPropertiesSet();
    assertTrue(output.getOut().contains("Starting initialization..."));
  }

  @Test
  void requestingInitialDataWithSuccess(CapturedOutput output) {
    mongoInit.requestingInitialData();
    times(1);
    assertTrue(output.getOut().contains("Requested 250 topCoins from 0 page"));
    times(2);
    assertTrue(output.getOut().contains("Requested 250 topCoins from 1 page"));
  }

  @Test
  void requestingInitialDataWithFailure() throws Exception {
    mongoInit.requestingInitialData();
  }

  @DisplayName("Checking if conversion and request works")
  @Test
  void shouldReturnCoinInfoSuccess() {
    List<String> strings = coins.stream().map(Objects::toString).toList();
    Mockito.when(apiHolder.getCoinInfo(strings)).thenReturn(Optional.of(resultOfGetCoinInfo));
  }

  @DisplayName("Passing null value to coinInfo")
  @Test
  @Nullable
  void shouldReturnCoinInfoFailureTestIfThrowsNullException()
      throws NullPointerException, NoSuchElementException {
    try {
      mongoInit.coinInfo(resultOfGetTopCoins);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  @Test
  void shouldPassDataSuccess(CapturedOutput output) {
    log.info(coins);
    mongoInit.passingData(coins);
    assertTrue(output.getOut().contains("Successfully added"));
  }

  @Test
  void shouldPassDataFailure() throws Exception {
    mongoInit.passingData(_coins);
    assertEquals(_coins.size(), 0);
  }

  void setupCoinInfoTest() {
    Mockito.when(this.apiHolder.getCoinInfo(List.of("btc")))
        .thenReturn(Optional.ofNullable(this.resultOfGetCoinInfo));

    List<Coin> topCoins = new ArrayList<>();
    Coin coin = new Coin();
    coin.setSymbol("btc");
    coin.setName("bitcoin");
    coin.setQuotes(new TreeMap<>());
    coin.setMarketCapRank(1);
    topCoins.add(coin);
    Result marketData = new Result();
    marketData.setCoins(topCoins);
    this.resultOfGetTopCoins = Optional.of(marketData);
  }

  @Test
  void coinInfoTest() {
    this.setupCoinInfoTest();
    var res = this.mongoInit.coinInfo(this.resultOfGetTopCoins);
    assertEquals(1, res.size());
    assertEquals("bitcoin", res.get(0).getName());
    assertEquals(coin.getImage_url(), res.get(0).getImage_url());
  }
}
