package com.capgemini.fs.coindashboard.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.capgemini.fs.coindashboard.cacheService.CacheService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CoinController.class)
public class CoinControllerSearchTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CacheService cacheService;

  @Test
  public void coinByRegexThrowsError400whenCoinNameLongerThen50() throws Exception {
    this.mockMvc
        .perform(get("/api/coins/find/012345678901234567890123456789012345678901234567890"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("regex cannot be longer than 50 characters")));
  }

  @Test
  public void coinByRegexThrowsError404WhenCacheServiceReturnsNull() throws Exception {

    this.mockMvc
        .perform(get("/api/coins/find/1234567890"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Could not find coin 1234567890")));
  }

  @Test
  public void coinByRegexResponsesCorrectly() throws Exception {
    when(cacheService.getCoinByRegex("bitcoin")).thenReturn(Optional.of("[{}]"));

    this.mockMvc
        .perform(get("/api/coins/find/bitcoin"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[{}]")));
  }
}
