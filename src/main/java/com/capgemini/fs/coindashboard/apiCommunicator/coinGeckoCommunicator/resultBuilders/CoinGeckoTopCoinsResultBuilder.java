package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
class CoinGeckoTopCoinsResultBuilder extends CoinGeckoMarketDataBuilderBaseClass{
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.TOP_COINS;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = super.buildSingleCoin(coinName, data);
    result.setMarketCapRank(data.get(this.mapper.MARKET_CAP_RANK).asInt());
    result.setQuotes(this.buildQuoteMap(data));
    return result;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    ArrayList<Coin> result = new ArrayList<>();
    ObjectMapper objMapper = new ObjectMapper();
    List<JsonNode> responseBodyConverted = objMapper.convertValue(data, new TypeReference<>() {});
    for (JsonNode coin : responseBodyConverted) {
      result.add(this.buildSingleCoin(coin.get(this.mapper.NAME).asText(), coin));
    }
    return result;
  }
}
