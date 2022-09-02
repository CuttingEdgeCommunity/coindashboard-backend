package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import java.util.List;
import org.junit.jupiter.api.Test;

class CoinMarketCapTranslatorTest {
  CoinMarketCapTranslator coinMarketCapTranslator = new CoinMarketCapTranslator();

  @Test
  void translate() {
    List<String> testSymbols = List.of("symbol1", "symbol2");
    assertEquals(
        testSymbols,
        coinMarketCapTranslator.translate(testSymbols, ApiCommunicatorMethodEnum.CURRENT_LISTING));
    assertEquals(
        testSymbols,
        coinMarketCapTranslator.translate(
            testSymbols, ApiCommunicatorMethodEnum.HISTORICAL_LISTING));
    assertEquals(
        testSymbols,
        coinMarketCapTranslator.translate(testSymbols, ApiCommunicatorMethodEnum.COIN_INFO));
    assertThrows(
        UnsupportedOperationException.class,
        () -> coinMarketCapTranslator.translate(testSymbols, ApiCommunicatorMethodEnum.TOP_COINS));
  }
}
