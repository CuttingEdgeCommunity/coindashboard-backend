package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.ID;
import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.CoinMarketCapBuilderBaseClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {
      CoinMarketCapTranslator.class,
      CoinMarketCapFacade.class,
      CoinMarketCapApiClient.class,
      CoinMarketCapFieldNameMapper.class
    })
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinMarketCapFacadeTest extends CoinMarketCapTestBaseClass {
  @Autowired private CoinMarketCapFieldNameMapper coinMarketCapFieldNameMapper;
  @Autowired private CoinMarketCapTranslator coinMarketCapTranslator;
  @MockBean private Set<CoinMarketCapBuilderBaseClass> builders;
  @MockBean private CoinMarketCapApiClient coinMarketCapApiClient;
  @MockBean private CoinMarketCapFacade coinMarketCapFacade;

  @BeforeEach
  void setup() throws JsonProcessingException {
    this.setupCorrectGetNames();
  }

  @Test
  void initTest() throws IOException {
    Mockito.when(coinMarketCapApiClient.getCoinsNames()).thenReturn(this.correctGetNamesR);
    CoinMarketCapFacade coinMarketCapFacade =
        new CoinMarketCapFacade(coinMarketCapTranslator, coinMarketCapApiClient, builders);
    coinMarketCapFacade.init();
    assertEquals(this.correctNames, coinMarketCapTranslator.translate(inputsymbols, NAME));
    assertEquals(this.correctIds, coinMarketCapTranslator.translate(inputsymbols, ID));
  }
}
