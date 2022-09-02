package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.CoinTranslator;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
final class CoinGeckoTranslator extends CoinTranslator {

  @Override
  public void initialize(Object data) {}

  @Override
  public List<String> translate(List<String> symbols, ApiCommunicatorMethodEnum method) {
    return switch (method) {
      case CURRENT_LISTING, HISTORICAL_LISTING -> this.translate(symbols, TranslationEnum.ID);
      default -> throw new IllegalStateException("Unexpected value: " + method);
    };
  }
}
