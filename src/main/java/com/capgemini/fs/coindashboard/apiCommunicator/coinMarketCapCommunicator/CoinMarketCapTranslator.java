package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.CoinTranslator;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.PlaceHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.TranslationEnum;
import java.util.List;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
final class CoinMarketCapTranslator extends CoinTranslator {
  @Autowired
  CoinMarketCapFieldNameMapper coinMarketCapFieldNameMapper;
  @Autowired private CoinMarketCapApiClient coinMarketCapApiClient;

  @Override
  public void initialize(Response response) {
    for (JsonNode coinJson : response.getResponseBody().get(coinMarketCapFieldNameMapper.DATA)) {
      String symbol = coinJson.get(coinMarketCapFieldNameMapper.SYMBOL).asText().toLowerCase();
      this.setTranslation(
          symbol,
          new PlaceHolder(
              coinJson.get(coinMarketCapFieldNameMapper.NAME).asText(),
              coinJson.get(coinMarketCapFieldNameMapper.ID).asText(),
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
