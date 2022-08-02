package com.capgemini.fs.coindashboard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class CoinControllerMockMvcStandaloneTest {

  private MockMvc mvc;

  @Mock private CoinRepository coinRepository;
  @Mock private CoinModelAssembler coinModelAssembler;

  @InjectMocks private CoinController coinController;

  // This object will be magically initialized by the initFields method below.
  private JacksonTester<Coin> jsonCoin;

  private ArrayList<MarketCapAndTime> firstCoin = new ArrayList<>();

  @BeforeEach
  public void setup() {
    firstCoin.add(new MarketCapAndTime(12L, 12.5));
    JacksonTester.initFields(this, new ObjectMapper());
    mvc =
        MockMvcBuilders.standaloneSetup(coinController)
            .setControllerAdvice(new CoinNotFoundAdvice())
            .build();
  }

  @Test
  public void canRetrieveByNameWhenExists() throws Exception {
    // given
    given(coinRepository.findById("BTC")).willReturn(Optional.of(new Coin("BTC", firstCoin)));
    System.out.println(coinRepository.findById("BTC"));

    // when
    MockHttpServletResponse response =
        mvc.perform(get("/coins/BTC").accept(MediaTypes.ALPS_JSON)).andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString())
        .isEqualTo(jsonCoin.write(new Coin("BTC", firstCoin)).getJson());
  }

  @Test
  public void canRetrieveByNameWhenDoesNotExist() throws Exception {
    // given
    given(coinRepository.findById("BTC")).willThrow(new CoinNotFoundException("BTC"));
    // when
    MockHttpServletResponse response =
        mvc.perform(get("/coins/BTC").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(response.getContentAsString()).isEqualTo("Could not find coin: BTC");
  }
}
