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
public class CoinControllerDetailsTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CacheService cacheService;

  @Test
  public void getAllThrowsError400whenCoinNameLongerThen50() throws Exception {
    this.mockMvc
        .perform(get("/api/coins/012345678901234567890123456789012345678901234567890"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("name cannot be longer than 50 characters")));
  }

  @Test
  public void getCoinMarketDataThrowsError404WhenCacheServiceReturnsNull() throws Exception {

    this.mockMvc
        .perform(get("/api/coins/1234567890"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Could not find coin 1234567890")));
  }

  @Test
  public void getCoinMarketDataResponsesCorrectly() throws Exception {
    when(cacheService.getCoinDetails("bitcoin")).thenReturn(Optional.of("[{}]"));

    this.mockMvc
        .perform(get("/api/coins/bitcoin"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[{}]")));
  }
}
