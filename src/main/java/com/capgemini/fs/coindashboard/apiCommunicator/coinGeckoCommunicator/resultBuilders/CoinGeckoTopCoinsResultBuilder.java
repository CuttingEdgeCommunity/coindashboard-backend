package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoTopCoinsResultBuilder extends CoinGeckoMarketDataBuilderBaseClass {
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

  @Override
  protected List<Price> buildPriceList(JsonNode data) {
    List<Price> priceList = new ArrayList<>();
    if (this.requestArgs.isInclude7dSparkline()) {
      LocalDateTime last_update =
          LocalDateTime.parse(data.get(mapper.LAST_UPDATE_DATE).textValue().replace("Z", ""));
      JsonNode prices = data.get(this.mapper.SPARKLINE).get(this.mapper.PRICE);
      int last_update_hour = last_update.getHour();
      last_update = last_update.withHour(7).withMinute(0).withSecond(0).withNano(0);
      if (last_update_hour < 7) {
        last_update = last_update.minusDays(1L);
      }
      LocalDateTime _7days_ago = last_update.minusDays(7L);
      for (int i = 0; i < prices.size(); i++) {
        _7days_ago = _7days_ago.plusHours(1L);
        priceList.add(new Price(prices.get(i).asDouble(), Timestamp.valueOf(_7days_ago).getTime()));
      }
    }
    return priceList;
  }

  @Override
  protected Price buildSinglePrice(JsonNode data) {
    return super.buildSinglePrice(data);
  }
}
