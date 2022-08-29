package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoinMarketCapTopCoinsResultBuilder extends CoinMarketCapBuilderBaseClass {

  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.TOP_COINS;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    ArrayList<Coin> result = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, JsonNode> responseBodyConverted =
        mapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, JsonNode> coin : responseBodyConverted.entrySet()) {
      result.add(this.buildSingleCoin(coin.getKey(), coin.getValue()));
    }
    return result;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = new Coin();
    result.setName(data.get(this.mapper.NAME).asText());
    result.setSymbol(data.get(this.mapper.SYMBOL).asText());
    result.setMarketCapRank(data.get(this.mapper.MARKET_CAP_RANK).asInt());
    result.setQuotes(this.buildQuoteMap(data.get(this.mapper.QUOTE)));
    return result;
  }

  @Override
  protected Map<String, Quote> buildQuoteMap(JsonNode data) {
    return null;
  }

  @Override
  protected Quote buildSingleQuote(JsonNode data) {
    return null;
  }

  @Override
  protected CurrentQuote buildCurrentQuote(JsonNode data) {
    return null;
  }

  @Override
  protected List<Price> buildPriceList(JsonNode data) {
    return null;
  }

  @Override
  protected List<Delta> buildDeltaList(JsonNode data) {
    return null;
  }

  @Override
  protected Price buildSinglePrice(JsonNode data) {
    return null;
  }

  @Override
  protected Delta buildSingleDelta(JsonNode data, IntervalEnum delta) {
    return null;
  }

  @Override
  protected List<Link> buildLinkList(JsonNode data) {
    return null;
  }

  @Override
  protected Link buildSingleLink(JsonNode data) {
    return null;
  }
}
