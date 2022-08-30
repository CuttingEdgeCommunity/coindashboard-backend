package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapHistoricalListingResultBuilder extends CoinMarketCapMarketDataBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.HISTORICAL_LISTING;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
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

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = super.buildSingleCoin(coinName, data);
    result.setQuotes(this.buildQuoteMap(data.get(this.mapper.QUOTES)));
    return result;
  }

  @Override
  protected Map<String, Quote> buildQuoteMap(JsonNode data) {
    Map<String, Quote> result = new HashMap<>();
    ObjectMapper objMapper = new ObjectMapper();
    List<ObjectNode> quotesConverted = objMapper.convertValue(data, new TypeReference<>() {});
    for (ObjectNode quote : quotesConverted) {
      Map<String, ObjectNode> quoteConverted =
          objMapper.convertValue(quote.get(this.mapper.QUOTE), new TypeReference<>() {});
      for (Map.Entry<String, ObjectNode> singleQuote : quoteConverted.entrySet()) {
        singleQuote
            .getValue()
            .put(this.mapper.INSERTED_QUOTE_NAME, singleQuote.getKey().toLowerCase());
        var builtQuote = this.buildSingleQuote(singleQuote.getValue());

        if (result.containsKey(singleQuote.getKey().toLowerCase())) {
          result
              .get(singleQuote.getKey().toLowerCase())
              .getChart()
              .addAll(0, builtQuote.getChart());
        } else {
          result.put(singleQuote.getKey().toLowerCase(), builtQuote);
        }
      }
    }
    return result;
  }

  @Override
  protected CurrentQuote buildCurrentQuote(JsonNode data) {
    return null;
  }

  @Override
  protected List<Price> buildPriceList(JsonNode data) {
    ArrayList<Price> result = new ArrayList<>();
    result.add(this.buildSinglePrice(data));
    return result;
  }

  @Override
  protected Price buildSinglePrice(JsonNode data) {
    Price result = new Price();
    result.setPrice(data.get(this.mapper.PRICE).asDouble());
    try {
      result.setTimestamp(
          TimeFormatter.convertStringToTimestamp(data.get(this.mapper.TIMESTAMP).asText())
              .getTime());
    } catch (ParseException ignored) {
    }
    return result;
  }
}
