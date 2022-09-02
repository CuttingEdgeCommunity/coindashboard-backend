package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.ID;
import static com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
// import org.junit.jupiter.api.BeforeEach;
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
      CoinMarketCapApiClient.class,
      CoinMarketCapFieldNameMapper.class
    })
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class CoinMarketCapTranslatorTest extends CoinMarketCapTestBaseClass {
  @Autowired private CoinMarketCapFieldNameMapper coinMarketCapFieldNameMapper;
  @Autowired private CoinMarketCapTranslator coinMarketCapTranslator;
  @MockBean private CoinMarketCapApiClient coinMarketCapApiClient;

  @BeforeEach
  void setup() throws JsonProcessingException {
    this.setupCorrectGetNames();
  }

  @Test
  void initialize() throws IOException {
    Mockito.when(coinMarketCapApiClient.getCoinsNames()).thenReturn(this.correctGetNamesR);
    coinMarketCapTranslator.initialize(coinMarketCapApiClient.getCoinsNames());
    assertEquals(this.correctNames, coinMarketCapTranslator.translate(inputsymbols, NAME));
    assertEquals(this.correctIds, coinMarketCapTranslator.translate(inputsymbols, ID));
  }

  @Test
  void translate() {
    assertEquals(
        this.inputsymbols,
        coinMarketCapTranslator.translate(inputsymbols, ApiCommunicatorMethodEnum.CURRENT_LISTING));
    assertEquals(
        this.inputsymbols,
        coinMarketCapTranslator.translate(
            inputsymbols, ApiCommunicatorMethodEnum.HISTORICAL_LISTING));
    assertEquals(
        "Unexpected value: " + ApiCommunicatorMethodEnum.TOP_COINS,
        assertThrows(
                IllegalStateException.class,
                () -> {
                  coinMarketCapTranslator.translate(
                      this.inputsymbols, ApiCommunicatorMethodEnum.TOP_COINS);
                },
                "Unexpected value: " + ApiCommunicatorMethodEnum.TOP_COINS)
            .getMessage());
  }
}
