package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.CoinTranslator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
final class CoinGeckoTranslator extends CoinTranslator {

  @Override
  public void initialize(Object data) {}

  @Override
  public List<String> translate(List<String> symbols, ApiCommunicatorMethodEnum method) {
    return null;
  }
}
