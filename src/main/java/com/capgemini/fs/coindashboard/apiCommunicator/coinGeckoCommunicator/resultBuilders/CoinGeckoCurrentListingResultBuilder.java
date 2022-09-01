package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
class CoinGeckoCurrentListingResultBuilder extends CoinGeckoMarketDataBuilderBaseClass{
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.CURRENT_LISTING;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    return null;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    return Collections.emptyList();
  }
}
