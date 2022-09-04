package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.ID;
import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders.CoinGeckoBuilderBaseClass;
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
      CoinGeckoTranslator.class,
      CoinGeckoFacade.class,
      CoinGeckoApiClient.class,
      CoinGeckoFieldNameMapper.class
    })
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinGeckoFacadeTest extends CoinGeckoTestBaseClass {
  @Autowired private CoinGeckoFieldNameMapper coinGeckoFieldNameMapper;
  @Autowired private CoinGeckoTranslator coinGeckoTranslator;
  @MockBean private Set<CoinGeckoBuilderBaseClass> builders;
  @MockBean private CoinGeckoApiClient coinGeckoApiClient;
  @MockBean private CoinGeckoFacade coinGeckoFacade;

  @BeforeEach
  void setup() throws JsonProcessingException {
    this.setupCorrectGetNames();
  }

  @Test
  void initTest() throws IOException {
    Mockito.when(coinGeckoApiClient.getCoinsNames()).thenReturn(this.correctGetNamesR);
    CoinGeckoFacade coinGeckoFacade =
        new CoinGeckoFacade(coinGeckoTranslator, coinGeckoApiClient, builders);
    coinGeckoFacade.init();
    assertEquals(this.correctNames, coinGeckoTranslator.translate(inputsymbols, NAME));
    assertEquals(this.correctIds, coinGeckoTranslator.translate(inputsymbols, ID));
  }
}
