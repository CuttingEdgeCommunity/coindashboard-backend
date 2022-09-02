package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.fasterxml.jackson.databind.JsonNode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
class CoinGeckoCurrentListingResultBuilder extends CoinGeckoMarketDataBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.CURRENT_LISTING;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = super.buildSingleCoin(coinName, data);
    result.setMarketCapRank(data.get(this.mapper.MARKET_CAP_RANK).asInt());
    result.setQuotes(this.buildQuoteMap(data.get(this.mapper.MARKET_DATA)));
    return result;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    return List.of(this.buildSingleCoin(data.get(this.mapper.NAME).asText().toLowerCase(), data));
  }

  @Override
  protected Map<String, Quote> buildQuoteMap(JsonNode data) {
    Map<String, Quote> result = new HashMap<>();
    List<String> vsCurrencies = (List<String>) requestArgs[1];

    for (String vsCurrency : vsCurrencies) {
      result.put(vsCurrency, this.buildSingleQuote(vsCurrency, data));
    }

    return result;
  }

  protected Quote buildSingleQuote(String vsCurrency, JsonNode data) {
    Quote result = new Quote();
    result.setVs_currency(vsCurrency);
    result.setCurrentQuote(this.buildCurrentQuote(vsCurrency, data));
    result.setChart(this.buildPriceList(data));
    return result;
  }

  protected CurrentQuote buildCurrentQuote(String vsCurrency, JsonNode data) {
    CurrentQuote result = new CurrentQuote();
    result.setMarket_cap(data.get(this.mapper.MARKET_CAP).get(vsCurrency).asDouble());
    result.setDeltas(this.buildDeltaList(vsCurrency, data));
    result.setDaily_volume(data.get(this.mapper.DAILY_VOLUME).get(vsCurrency).asDouble());
    result.setPrice(data.get(this.mapper.CURRENT_PRICE).get(vsCurrency).asDouble());
    try {
      result.setLast_update_timestamp(
          TimeFormatter.convertStringToTimestamp(data.get(this.mapper.LAST_UPDATE_DATE).asText())
              .getTime());
    } catch (ParseException ignored) {
    }
    return result;
  }

  protected List<Delta> buildDeltaList(String vsCurrency, JsonNode data) {
    List<Delta> result = new ArrayList<>();
    for (IntervalEnum intervalEnum :
        List.of(IntervalEnum.ONE_HOUR, IntervalEnum.ONE_DAY, IntervalEnum.SEVEN_DAY)) {
      result.add(this.buildSingleDelta(vsCurrency, data, intervalEnum));
    }
    return result;
  }

  protected Delta buildSingleDelta(String vsCurrency, JsonNode data, IntervalEnum delta) {
    Delta result = new Delta();
    result.setInterval(delta.name());
    double deltaPct = (data.get(this.mapper.DELTA_MAP.get(delta)).get(vsCurrency).asDouble());
    result.setPct(deltaPct);
    result.setNominal(
        this.calculateNominalDelta(
            data.get(this.mapper.CURRENT_PRICE).get(vsCurrency).asDouble(), deltaPct));
    return result;
  }
}
