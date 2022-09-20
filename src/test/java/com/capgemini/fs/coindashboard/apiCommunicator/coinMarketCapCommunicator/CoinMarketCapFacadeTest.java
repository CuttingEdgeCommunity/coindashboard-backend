package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.ID;
import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilderDirector;
import java.io.IOException;
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
      CoinMarketCapFieldNameMapper.class,
      ResultBuilderDirector.class
    })
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinMarketCapFacadeTest extends CoinMarketCapTestBaseClass {
  @MockBean private CoinMarketCapApiClient client;
  @Autowired private CoinMarketCapFacade facade;
  @Autowired private CoinMarketCapTranslator translator;

  @Test
  void initTest() throws IOException {
    this.setupCorrectGetNames();
    Mockito.when(client.getCoinsNames()).thenReturn(this.correctGetNamesR);
    facade.init();
    assertEquals(this.correctNames, translator.translate(inputsymbols, NAME));
    assertEquals(this.correctIds, translator.translate(inputsymbols, ID));
  }
}
