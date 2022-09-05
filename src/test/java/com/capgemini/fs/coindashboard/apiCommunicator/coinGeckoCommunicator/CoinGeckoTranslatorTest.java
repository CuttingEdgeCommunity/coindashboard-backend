package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.ID;
import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
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
    classes = {CoinGeckoTranslator.class, CoinGeckoApiClient.class, CoinGeckoFieldNameMapper.class})
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinGeckoTranslatorTest extends CoinGeckoTestBaseClass {
  @Autowired CoinGeckoTranslator coinGeckoTranslator;
  @MockBean CoinGeckoApiClient coinGeckoApiClient;

  @BeforeEach
  void setup() throws JsonProcessingException {
    this.setupCorrectGetNames();
  }

  @Test
  void initialize() throws IOException {
    Mockito.when(coinGeckoApiClient.getCoinsNames()).thenReturn(this.correctGetNamesR);
    coinGeckoTranslator.initialize(coinGeckoApiClient.getCoinsNames());
    assertEquals(this.correctNames, coinGeckoTranslator.translate(this.inputsymbols, NAME));
    assertEquals(this.correctIds, coinGeckoTranslator.translate(this.inputsymbols, ID));
  }

  @Test
  void translate() {
    assertEquals(
        this.correctIds,
        coinGeckoTranslator.translate(
            this.inputsymbols, ApiCommunicatorMethodEnum.CURRENT_LISTING));
    assertEquals(
        this.correctIds,
        coinGeckoTranslator.translate(
            this.inputsymbols, ApiCommunicatorMethodEnum.HISTORICAL_LISTING));
    assertEquals(
        "Unexpected value: " + ApiCommunicatorMethodEnum.TOP_COINS,
        assertThrows(
                IllegalStateException.class,
                () -> {
                  coinGeckoTranslator.translate(
                      this.inputsymbols, ApiCommunicatorMethodEnum.TOP_COINS);
                },
                "Unexpected value: " + ApiCommunicatorMethodEnum.TOP_COINS)
            .getMessage());
  }
}
