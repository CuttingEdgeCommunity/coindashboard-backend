package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoHistoricalListingResultBuilder extends CoinGeckoMarketDataBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.HISTORICAL_LISTING;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    return List.of(this.buildSingleCoin((String) requestArgs[0], data));
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = new Coin();
    result.setName(coinName);
    result.setQuotes(this.buildQuoteMap(data));
    return result;
  }

  @Override
  protected Map<String, Quote> buildQuoteMap(JsonNode data) {
    Map<String, Quote> result = new HashMap<>();
    result.put((String) requestArgs[1], buildSingleQuote(data));

    return result;
  }

  @Override
  protected Quote buildSingleQuote(JsonNode data) {
    Quote result = new Quote();
    result.setVs_currency((String) requestArgs[1]);
    result.setCurrentQuote(this.buildCurrentQuote(data));
    result.setChart(this.buildPriceList(data.get(this.mapper.PRICES)));
    return result;
  }

  @Override
  protected CurrentQuote buildCurrentQuote(JsonNode data) {
    return null;
  }

  @Override
  protected List<Price> buildPriceList(JsonNode data) {
    ArrayList<Price> result = new ArrayList<>();
    for (JsonNode jsonNode : data) {
      result.add(this.buildSinglePrice(jsonNode));
    }
    return result;
  }

  @Override
  protected Price buildSinglePrice(JsonNode data) {
    Price result = new Price();
    result.setPrice(data.get(1).asDouble());
    result.setTimestamp(data.get(0).asLong());
    return result;
  }
}
