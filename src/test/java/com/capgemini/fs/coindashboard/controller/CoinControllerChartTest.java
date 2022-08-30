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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc
class CoinControllerChartTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CacheService cacheService;

  @Test
  public void getAllThrowsError400whenChart_toBeforeChart_from() throws Exception {
    this.mockMvc
        .perform(get("/api/coins/bitcoin/chart").param("chart_from", "5").param("chart_to", "2"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("chart_to cannot be before chart_from")));
  }

  @Test
  public void getAllThrowsError400whenCoinNameLongerThen50() throws Exception {
    this.mockMvc
        .perform(get("/api/coins/0123456789012345678901234567890123456789012345678901/chart"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("name cannot be longer than 50 characters")));
  }

  @Test
  public void getCoinMarketDataResponsesCorrectlyWithQueryParams() throws Exception {
    when(cacheService.getChart("bitcoin", 2, 3)).thenReturn(Optional.of("[{}]"));

    this.mockMvc
        .perform(get("/api/coins/bitcoin/chart").param("chart_from", "2").param("chart_to", "3"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[{}]")));
  }

  @Test
  public void getCoinMarketDataResponsesCorrectlyWithoutQueryParams() throws Exception {
    when(cacheService.getChart("bitcoin", 0, 0)).thenReturn(Optional.of("[{}]"));

    this.mockMvc
        .perform(get("/api/coins/bitcoin/chart"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[{}]")));
  }
}
