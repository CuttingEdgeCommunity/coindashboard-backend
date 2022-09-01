package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    classes = {CoinGeckoTranslator.class, CoinGeckoApiClient.class, CoinGeckoFieldNameMapper.class})
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinGeckoTranslatorTest extends CoinGeckoTestBaseClass {
  @Autowired CoinGeckoTranslator coinGeckoTranslator;
  @MockBean CoinGeckoApiClient coinGeckoApiClient;

  @Test
  void initialize() throws IOException {
    this.setupCorrectGetNames();
    Mockito.when(coinGeckoApiClient.getCoinsNames()).thenReturn(this.correctGetNamesR);
    coinGeckoTranslator.initialize(coinGeckoApiClient.getCoinsNames());
    assertEquals(this.correctTranslationMap, coinGeckoTranslator.getTranslationMap());
  }

  @Test
  void translate() {}
}
