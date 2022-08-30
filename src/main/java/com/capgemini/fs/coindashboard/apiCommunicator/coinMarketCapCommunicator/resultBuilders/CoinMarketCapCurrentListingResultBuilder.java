package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapCurrentListingResultBuilder extends CoinMarketCapMarketDataBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.CURRENT_LISTING;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = super.buildSingleCoin(coinName, data);
    result.setMarketCapRank(data.get(this.mapper.MARKET_CAP_RANK).asInt());
    result.setQuotes(this.buildQuoteMap(data.get(this.mapper.QUOTE)));
    return result;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    ArrayList<Coin> result = new ArrayList<>();
    ObjectMapper objMapper = new ObjectMapper();
    Map<String, ObjectNode> responseBodyConverted =
        objMapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, ObjectNode> coin : responseBodyConverted.entrySet()) {
      if (coin.getValue().size() == 0) {
        continue;
      }
      result.add(
          this.buildSingleCoin(coin.getValue().get(this.mapper.NAME).asText(), coin.getValue()));
    }
    return result;
  }
}
