package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Disabled;
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
      CoinMarketCapApiClient.class,
      CoinMarketCapFieldNameMapper.class
    })
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinMarketCapTranslatorTest extends CoinMarketCapTestBaseClass {
  @Autowired private CoinMarketCapTranslator coinMarketCapTranslator;
  @MockBean private CoinMarketCapApiClient coinMarketCapApiClient;

  @Test
  void initialize() throws IOException {
    this.setupCorrectGetNames();
    Mockito.when(coinMarketCapApiClient.getCoinsNames()).thenReturn(this.correctGetNamesR);
    coinMarketCapTranslator.initialize(coinMarketCapApiClient.getCoinsNames());
    assertEquals(this.correctTranslationMap, coinMarketCapTranslator.getTranslationMap());
  }

  @Test
  void translate() {}
}
