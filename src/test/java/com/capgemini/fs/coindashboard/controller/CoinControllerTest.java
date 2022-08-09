package com.capgemini.fs.coindashboard.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.capgemini.fs.coindashboard.controller.exceptionHandler.CoinNotFoundAdvice;
import com.capgemini.fs.coindashboard.controller.exceptionHandler.CoinNotFoundException;
import com.capgemini.fs.coindashboard.service.CoinService;
import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import com.capgemini.fs.coindashboard.utilDataTypes.MarketCapAndTime;
import java.util.ArrayList;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class CoinControllerTest {
  private static final Logger log = LogManager.getLogger(CoinController.class);
  private MockMvc mvc;
  @Mock private CoinService coinService;
  @Mock private CoinRepository coinRepository;
  @Mock private CoinModelAssembler coinModelAssembler;

  @InjectMocks private CoinController coinController;

  public ArrayList<MarketCapAndTime> firstCoin = new ArrayList<>();

  @BeforeEach
  public void setup() {
    firstCoin.add(new MarketCapAndTime(12L, 12.5));
    mvc =
        MockMvcBuilders.standaloneSetup(coinController)
            .setControllerAdvice(new CoinNotFoundAdvice())
            .build();
  }

  @Test
  public void canRetrieveByAll() throws Exception {
    Coin coin = new Coin("BTC", firstCoin);
    ArrayList<Coin> coins = new ArrayList<>();
    coins.add(coin);
    Mockito.when(coinService.getCoins()).thenReturn(coins);
    mvc.perform(get("/coins")).andDo(MockMvcResultHandlers.log()).andExpect(status().isOk());
  }

  @Test
  public void canRetrieveByNameWhenExists() throws Exception {
    Coin coin = new Coin("BTC", firstCoin);
    Mockito.when(coinService.getCoinByName("BTC")).thenReturn(Optional.of(coin));
    mvc.perform(get("/coins/BTC")).andDo(MockMvcResultHandlers.log()).andExpect(status().isOk());
  }

  @Test
  public void canRetrieveByNameWhenDoesNotExist() throws Exception {
    given(coinService.getCoinByName("BTC")).willThrow(new CoinNotFoundException("BTC"));
    // when
    MockHttpServletResponse response =
        mvc.perform(get("/coins/BTC").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(response.getContentAsString()).isEqualTo("Could not find coin BTC");
  }
}
