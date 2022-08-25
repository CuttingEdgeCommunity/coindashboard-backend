package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.capgemini.fs.coindashboard.cacheService.CacheService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest
@Disabled
class ApiErrorControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private CacheService cacheService;

  @Test
  public void getUnknownRespondsWithError400AndMapOfLinks() throws Exception {
    this.mockMvc
        .perform(get("/api/cons"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("links")));
    this.mockMvc
        .perform(get("/coins"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("links")));
  }
}
