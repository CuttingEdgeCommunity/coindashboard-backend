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
      //      String date_string = data.get(mapper.LAST_UPDATE_DATE).textValue();
      // Date last_update = Date.from(Instant.parse(data.get(mapper.LAST_UPDATE_DATE).textValue()));
      //      System.out.println(date_string.replace("Z",""));
      //      LocalDateTime test = LocalDateTime.parse(date_string.replace("Z",""));
      LocalDateTime last_update =
          LocalDateTime.parse(data.get(mapper.LAST_UPDATE_DATE).textValue().replace("Z", ""));
      //      System.out.println(test);
      //      System.out.println(test.minusHours(1l));
      //      System.out.println(test.minusHours(2l));
      //      System.out.println(Timestamp.valueOf(test.minusHours(2l)).getTime());
      //      System.out.println(data.get(this.mapper.SPARKLINE).get(this.mapper.PRICE).size());
      JsonNode prices = data.get(this.mapper.SPARKLINE).get(this.mapper.PRICE);
      for (int i = prices.size() - 1; i >= 0; i--) {
        priceList.add(
            new Price(prices.get(i).asDouble(), Timestamp.valueOf(last_update).getTime()));
        last_update = last_update.minusHours(1l);
      }
      //      for (JsonNode node : data.get(this.mapper.SPARKLINE).get(this.mapper.PRICE)){
      //        priceList.add(new Price(node.asDouble(),0L));
      //      }
    }
    return priceList;
  }

  @Override
  protected Price buildSinglePrice(JsonNode data) {
    return super.buildSinglePrice(data);
  }
}
