package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
class CoinGeckoTopCoinsResultBuilder extends CoinGeckoMarketDataBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.TOP_COINS;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    return null;
  }
}
