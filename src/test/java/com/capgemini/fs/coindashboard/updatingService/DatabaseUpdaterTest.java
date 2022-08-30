package com.capgemini.fs.coindashboard.updatingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.UpdateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Disabled
class DatabaseUpdaterTest {
  private final DatabaseUpdater databaseUpdater = new DatabaseUpdater();
  private final ApiHolder apiHolder = Mockito.mock(ApiHolder.class);
  private final UpdateQueries updateQueries = Mockito.mock(UpdateQueries.class);

  private final GetQueries getQueries = Mockito.mock(GetQueries.class);
  private final String vsCurrency = "usd";

  @BeforeEach
  void setup() {

    Quote quote = new Quote(vsCurrency, null, null);
    Map<String, Quote> quotes = new HashMap<>();
    quotes.put(vsCurrency, quote);
    Coin coin = new Coin(1234, "BLABLA", 1, "btc", "", 123, false, null, null, null, quotes);
    List<Coin> coins = new ArrayList<>();
    coins.add(coin);

    databaseUpdater.setUpdateQueries(updateQueries);
    databaseUpdater.setApiHolder(apiHolder);
  }

  @Test
  void currentQuoteUpdatesIfEnabledIsFalse() {
    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrency)).thenReturn(coins);

    databaseUpdater.setEnabled(false);
    databaseUpdater.currentQuoteUpdates();
  }

  @Test
  void currentQuoteUpdatesIfEnabledIsTrueAndResponseIsNotNull() {
    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrency)).thenReturn(coins);

    databaseUpdater.setEnabled(true);
    assertTrue(databaseUpdater.currentQuoteUpdates());
  }

  @Test
  void currentQuoteUpdatesIfEnabledIsTrueAndResponseIsNull() {
    Mockito.when(apiHolder.getTopCoins(250, 0, vsCurrency)).thenReturn(null);

    databaseUpdater.setEnabled(true);
    assertEquals(databaseUpdater.currentQuoteUpdates(), new NoResultException());
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
