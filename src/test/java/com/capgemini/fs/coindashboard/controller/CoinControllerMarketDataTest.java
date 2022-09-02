package com.capgemini.fs.coindashboard.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.capgemini.fs.coindashboard.cacheService.CacheService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CoinControllerMarketDataTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CacheService cacheService;

  @Test
  public void getCoinMarketDataResponsesCorrectlyWithOnlyPATHParams() throws Exception {
    when(cacheService.getCoinMarketData("bitcoin", "usd")).thenReturn(Optional.of("[{}]"));

    this.mockMvc
        .perform(get("/api/coins/bitcoin/marketdata"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[{}]")));
  }

  @Test
  public void getCoinMarketDataResponsesCorrectlyWithQueryParams() throws Exception {
    when(cacheService.getCoinMarketData("bitcoin", "pln")).thenReturn(Optional.of("[{}]"));

    this.mockMvc
        .perform(get("/api/coins/bitcoin/marketdata").param("vs_currency", "pln"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[{}]")));
  }

  @Test
  public void getCoinMarketDataThrowsError400WhenPathParamOver50CharactersLongWithOnlyPATHParams()
      throws Exception {

    this.mockMvc
        .perform(get("/api/coins/123456789012345678901234567890123456789012345678901/marketdata"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("symbol cannot be longer than 50 characters")));
  }

  @Test
  public void getCoinMarketDataThrowsError400WhenPathParamOver50CharactersLong() throws Exception {

    this.mockMvc
        .perform(
            get("/api/coins/123456789012345678901234567890123456789012345678901/marketdata")
                .param("vs_currency", "pln"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("symbol cannot be longer than 50 characters")));
  }

  @Test
  public void getCoinMarketDataThrowsError400WhenVs_CurrencyIsOver10CharactersLong()
      throws Exception {

    this.mockMvc
        .perform(get("/api/coins/12345678901/marketdata").param("vs_currency", "12345678901"))
        .andExpect(status().isBadRequest())
        .andExpect(
            content().string(containsString("vs_currency cannot be longer than 10 characters")));
  }

  @Test
  public void getCoinMarketDataThrowsError404WhenCacheServiceReturnsNull() throws Exception {

    this.mockMvc
        .perform(get("/api/coins/12345678901/marketdata").param("vs_currency", "12345601"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Could not find coin 12345678901")));
  }

  @Test
  public void getCoinMarketDataThrowsError404WhenVs_CurrencyDoesNotExist() throws Exception {

    when(cacheService.getCoinMarketData("bitcoin", "pln")).thenReturn(Optional.of("[{}]"));

    this.mockMvc
        .perform(get("/api/coins/bitcoin/marketdata").param("vs_currency", "12345601"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Could not find coin bitcoin")));
  }
}
