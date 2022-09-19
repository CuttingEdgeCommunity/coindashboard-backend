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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@ContextConfiguration(classes = {DatabaseUpdater.class})
class DatabaseUpdaterTest {
  @Autowired private DatabaseUpdater databaseUpdater;
  @MockBean private ApiHolder apiHolder;
  @MockBean private UpdateQueries updateQueries;
  @MockBean private CreateQueries createQueries;
  @MockBean private GetQueries getQueries;
  private final String vsCurrency = "usd";
  private List<String> vsCurrencies = new ArrayList<>();
  private Quote quote = new Quote(vsCurrency, null, null);
  private Map<String, Quote> quotes = new HashMap<>();
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

  //  @Test
  //  void currentQuoteUpdatesIfEnabledIsTrueAndCoinExists() {
  //    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies))
  //        .thenReturn(Optional.of(resultOfGetTopCoins));
  //    // Mockito.when(getQueries.isCoinInDBBySymbol("btc")).thenReturn(true);
  //    Mockito.when(getQueries.getCoinsNotInDBBySymbols(List.of("btc"))).thenReturn(List.of());
  //    Mockito.when(apiHolder.getCoinInfo(List.of())).thenReturn(Optional.of(resultEmpty));
  //    Mockito.when(updateQueries.updateTopCoinsTransaction2(resultOfGetTopCoins, resultEmpty))
  //        .thenReturn(true);
  //    databaseUpdater.setEnabled(true);
  //    assertTrue(databaseUpdater.currentQuoteUpdates());
  //  }
  //
  //  @Test
  //  void currentQuoteUpdatesIfEnabledIsTrueAndCoinDoesNotExistsAndGetCoinInfoReturnsNull() {
  //    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies))
  //        .thenReturn(Optional.of(resultOfGetTopCoins));
  //    // Mockito.when(getQueries.isCoinInDBBySymbol("btc")).thenReturn(false);
  //
  // Mockito.when(getQueries.getCoinsNotInDBBySymbols(List.of("btc"))).thenReturn(List.of("btc"));
  //    databaseUpdater.setEnabled(true);
  //    Mockito.when(apiHolder.getCoinInfo(List.of("btc"))).thenReturn(Optional.of(resultEmpty));
  //    Mockito.when(updateQueries.updateTopCoinsTransaction2(resultOfGetTopCoins, resultEmpty))
  //        .thenReturn(true);
  //    assertTrue(databaseUpdater.currentQuoteUpdates());
  //  }

  //  @Test
  //  void currentQuoteUpdatesIfEnabledIsTrueAndCoinDoesNotExists() {
  //    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrencies))
  //        .thenReturn(Optional.of(resultOfGetTopCoins));
  //    Mockito.when(apiHolder.getCoinInfo(List.of("btc")))
  //        .thenReturn(Optional.of(resultOfGetCoinInfo));
  //    // Mockito.when(getQueries.isCoinInDBBySymbol("btc")).thenReturn(false);
  //
  // Mockito.when(getQueries.getCoinsNotInDBBySymbols(List.of("btc"))).thenReturn(List.of("btc"));
  //    Mockito.when(createQueries.createCoinDocument(coin)).thenReturn(true);
  //    databaseUpdater.setEnabled(true);
  //    assertTrue(databaseUpdater.currentQuoteUpdates());
  //  }

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
}
