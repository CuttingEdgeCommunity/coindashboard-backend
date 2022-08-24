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
public class CoinControllerAllTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private CacheService cacheService;

  @Test
  public void getAllThrowsError400whenTakeIsSmallerThen0() throws Exception {
    this.mockMvc
        .perform(get("/api/coins").param("take", "-1"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Take has to be bigger or equal to 0")));
  }

  @Test
  public void getAllThrowsError400whenTakeIsBiggerThen300() throws Exception {
    this.mockMvc
        .perform(get("/api/coins").param("take", "301"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Take cannot be more than 300")));
  }

  @Test
  public void getAllThrowsError400whenPageIsSmallerThen0() throws Exception {
    this.mockMvc
        .perform(get("/api/coins").param("page", "-1"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Page has to be bigger or equal to 0")));
  }

  @Test
  public void getAllThrowsError500WhenServerSendsNull() throws Exception {
    when(cacheService.getCoinInfo(10, 0)).thenReturn(Optional.empty());

    this.mockMvc
        .perform(get("/api/coins"))
        .andExpect(status().is5xxServerError())
        .andExpect(content().string(containsString("Server is not responding...")));
  }

  @Test
  public void getAllResponsesCorrectlyWithoutQueryParams() throws Exception {
    when(cacheService.getCoinInfo(10, 0)).thenReturn(Optional.of("[]"));

    this.mockMvc
        .perform(get("/api/coins"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[]")));
  }

  @Test
  public void getAllResponsesCorrectlyWithQueryParams() throws Exception {
    when(cacheService.getCoinInfo(5, 1)).thenReturn(Optional.of("[]"));

    this.mockMvc
        .perform(get("/api/coins").param("take", "5").param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("[]")));
  }
}
