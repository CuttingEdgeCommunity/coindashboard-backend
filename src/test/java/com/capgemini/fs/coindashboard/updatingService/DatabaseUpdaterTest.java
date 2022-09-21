package com.capgemini.fs.coindashboard.updatingService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.UpdateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.utils.AsyncService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseUpdater.class})
class DatabaseUpdaterTest {
  @Autowired private DatabaseUpdater databaseUpdater;
  @MockBean private ApiHolder apiHolder;
  @MockBean private UpdateQueries updateQueries;
  @MockBean private CreateQueries createQueries;
  @MockBean private GetQueries getQueries;

  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  private AsyncService asyncService;

  private final String vsCurrency = "usd";
  private List<String> vsCurrencies = new ArrayList<>();
  private Quote quote = new Quote(vsCurrency, null, null);
  private Map<String, Quote> quotes = new HashMap<>();
  private Map<String, Integer> ranks = new HashMap<>();
  private Coin coin;
  private List<Coin> coins = new ArrayList<>();
  private Result resultOfGetTopCoins;
  private Result resultOfGetCoinInfo;
  private Result resultEmpty;

  @BeforeEach
  void setup() {
    quotes.put(vsCurrency, quote);
    vsCurrencies.add(vsCurrency);
    coin = new Coin("1234", "BLABLA", "btc", 1, "", 123L, false, null, null, null, quotes);
    coins.add(coin);
    resultOfGetTopCoins =
        new Result(ApiProviderEnum.COIN_MARKET_CAP, ResultStatus.SUCCESS, "khgfoiwejf", coins);
    resultOfGetCoinInfo =
        new Result(ApiProviderEnum.COIN_MARKET_CAP, ResultStatus.SUCCESS, "khgfoiwejf", coins);
    resultEmpty =
        new Result(
            ApiProviderEnum.COIN_MARKET_CAP,
            ResultStatus.FAILURE,
            "Cannot invoke for empty list",
            null);
  }

  @Test
  void currentQuoteUpdatesIfEnabledIsFalse() {
    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies))
        .thenReturn(Optional.of(resultOfGetTopCoins));

    databaseUpdater.setEnabled(false);
    assertFalse(databaseUpdater.currentQuoteUpdates());
  }

  @Test
  void currentQuoteUpdatesIfEnabledIsTrueAndResponseIsNull() {
    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies)).thenReturn(Optional.empty());

    databaseUpdater.setEnabled(true);
    assertFalse(databaseUpdater.currentQuoteUpdates());
  }

  @Test
  void chartUpdateIfEnabledIsFalse() {
    databaseUpdater.setEnabled(false);
    assertFalse(databaseUpdater.chartUpdate());
  }

  @Test
  void chartUpdateIfEnabledIsTrue() {
    databaseUpdater.setEnabled(true);
    assertTrue(databaseUpdater.chartUpdate());
  }

  @Test
  void ComparerCurrentAndPreviousResultTestWith2SameListsAllCoinsAlreadyInDb() {
    ObjectMapper mapper = new ObjectMapper();
    String prev_coins_JsonString;
    try {
      prev_coins_JsonString = mapper.writeValueAsString(coins);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies))
        .thenReturn(Optional.of(resultOfGetTopCoins));
    Mockito.when(getQueries.getCoinsSimple(250, 0)).thenReturn(prev_coins_JsonString);
    Mockito.when(getQueries.isCoinInDBBySymbol("btc")).thenReturn(true);
    databaseUpdater.setEnabled(true);
    databaseUpdater.currentQuoteUpdates();
  }

  @Test
  void CurrentQuoteUpdatesTestWith2DiffListsCoinsNotInDb() {
    ObjectMapper mapper = new ObjectMapper();
    String prev_coins_JsonString;
    Coin eth = new Coin("1234", "Ethereum", "eth", 2, "", 123L, false, null, null, null, quotes);
    List<Coin> curr_coins = List.of(eth);
    resultOfGetTopCoins.setCoins(curr_coins);
    try {
      prev_coins_JsonString = mapper.writeValueAsString(coins);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies))
        .thenReturn(Optional.of(resultOfGetTopCoins));
    Mockito.when(getQueries.getCoinsSimple(250, 0)).thenReturn(prev_coins_JsonString);
    Mockito.when(getQueries.isCoinInDBBySymbol("eth")).thenReturn(false);
    Mockito.when(apiHolder.getCoinInfo(List.of("eth")))
        .thenReturn(Optional.of(resultOfGetTopCoins));
    databaseUpdater.setEnabled(true);
    assertTrue(databaseUpdater.currentQuoteUpdates());
  }
  //  @Test
  //  void CurrentQuoteUpdatesTestWith2DiffListsCoinsNotInDbWithDuplicates() {
  //    ObjectMapper mapper = new ObjectMapper();
  //    String prev_coins_JsonString;
  //    Coin eth = new Coin("1234", "Ethereum", "eth", 2, "", 123L, false, null, null, null,
  // quotes);
  //    List<Coin> curr_coins = List.of(eth);
  //    resultOfGetTopCoins.setCoins(curr_coins);
  //    coins.add(coin);
  //    try {
  //      prev_coins_JsonString = mapper.writeValueAsString(coins);
  //    } catch (JsonProcessingException e) {
  //      throw new RuntimeException(e);
  //    }
  //    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies))
  //        .thenReturn(Optional.of(resultOfGetTopCoins));
  //    Mockito.when(getQueries.getCoinsSimple(250, 0)).thenReturn(prev_coins_JsonString);
  //    Mockito.when(getQueries.isCoinInDBBySymbol("eth")).thenReturn(false);
  //    Mockito.when(apiHolder.getCoinInfo(List.of("eth")))
  //        .thenReturn(Optional.of(resultOfGetTopCoins));
  //    doNothing().when(updateQueries).removeDuplicates("1234");
  //    databaseUpdater.setEnabled(true);
  //    assertTrue(databaseUpdater.currentQuoteUpdates());
  //  }
}
