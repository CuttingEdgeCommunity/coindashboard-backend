package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.CoinTranslator;
import java.util.List;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.PlaceHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
final class CoinGeckoTranslator extends CoinTranslator {
  @Autowired
  private CoinGeckoApiClient coinGeckoApiClient;
  @Autowired
  CoinGeckoFieldNameMapper coinGeckoFieldNameMapper;


  @Override
  public void initialize(Response response) {
    for (JsonNode coinJson : response.getResponseBody()) {
      String symbol = coinJson.get(coinGeckoFieldNameMapper.SYMBOL).asText().toLowerCase();
      this.setTranslation(
          symbol,
          new PlaceHolder(
              coinJson.get(coinGeckoFieldNameMapper.NAME).asText(),
              coinJson.get(coinGeckoFieldNameMapper.ID).asText(),
              symbol));
    }
    log.info("PlaceHolderMap with lowercase 'symbol' initialized");
  }

  @Override
  public List<String> translate(List<String> symbols, ApiCommunicatorMethodEnum method) {
    return switch (method) {
      case CURRENT_LISTING, HISTORICAL_LISTING -> this.translate(symbols, TranslationEnum.ID);
      default -> throw new IllegalStateException("Unexpected value: " + method);
    };
  }
}
