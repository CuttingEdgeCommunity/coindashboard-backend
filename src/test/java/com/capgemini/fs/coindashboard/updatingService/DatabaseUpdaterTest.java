package com.capgemini.fs.coindashboard.updatingService;

import com.capgemini.fs.coindashboard.CRUDService.queries.UpdateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseUpdaterTest {
  private final DatabaseUpdater databaseUpdater = new DatabaseUpdater();
  private final ApiHolder apiHolder = Mockito.mock(ApiHolder.class);
  private final UpdateQueries updateQueries = Mockito.mock(UpdateQueries.class);
  private final CoinMarketDataResult coinMarketData = new CoinMarketDataResult();
  private final QuoteDto quoteDto = new QuoteDto();
  private final String coinNameForApi = "btc";

  @BeforeEach
  void setup() {
    String vsCurrency = "usd";
    String coinNameForQuery = "Bitcoin";

    CoinMarketDataDto coinMarketDataDto = new CoinMarketDataDto(
        coinNameForApi, Map.of(vsCurrency, quoteDto));

    coinMarketData.setCoinMarketDataDTOS(List.of(coinMarketDataDto));

    databaseUpdater.setUpdateQueries(updateQueries);
    databaseUpdater.setApiHolder(apiHolder);

    Mockito.when(updateQueries.updateCoinCurrentQuote(coinNameForQuery,
        quoteDto, vsCurrency)).thenReturn(true);

    Mockito.when(updateQueries.updateEveryCoinPriceChart())
        .thenReturn(true);
  }

  @Test
  void singleCoinUpdateIfEnabledIsFalse() {
    Mockito.when(apiHolder.getCoinMarketData(coinNameForApi))
        .thenReturn(coinMarketData);

    databaseUpdater.setEnabled(false);
    assertFalse(databaseUpdater.singleCoinUpdate());
  }

  @Test
  void singleCoinUpdateIfEnabledIsTrueAndResponseIsNotNull() {
    Mockito.when(apiHolder.getCoinMarketData(coinNameForApi))
        .thenReturn(coinMarketData);

    databaseUpdater.setEnabled(true);
    assertTrue(databaseUpdater.singleCoinUpdate());
  }

  @Test
  void singleCoinUpdateIfEnabledIsTrueAndResponseIsNull() {
    Mockito.when(apiHolder.getCoinMarketData(coinNameForApi))
        .thenReturn(null);

    databaseUpdater.setEnabled(true);
    assertFalse(databaseUpdater.singleCoinUpdate());
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
