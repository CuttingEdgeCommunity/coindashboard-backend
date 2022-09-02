package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CoinGeckoMarketDataBuilderBaseClass extends CoinGeckoBuilderBaseClass {
  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = new Coin();
    result.setName(data.get(this.mapper.NAME).asText());
    result.setSymbol(data.get(this.mapper.SYMBOL).asText());
    return result;
  }

  @Override
  protected Map<String, Quote> buildQuoteMap(JsonNode data) {
    Map<String, Quote> result = new HashMap<>();

    ((ObjectNode) data).put("inserted_quote_name", (String) requestArgs[2]);
    result.put(data.get(mapper.INSERTED_QUOTE_NAME).asText(), this.buildSingleQuote(data));

    return result;
  }

  @Override
  protected Quote buildSingleQuote(JsonNode data) {
    Quote result = new Quote();
    result.setVs_currency(data.get(this.mapper.INSERTED_QUOTE_NAME).asText());
    result.setCurrentQuote(this.buildCurrentQuote(data));
    result.setChart(this.buildPriceList(data));
    return result;
  }

  @Override
  protected CurrentQuote buildCurrentQuote(JsonNode data) {
    CurrentQuote result = new CurrentQuote();
    result.setMarket_cap(data.get(this.mapper.MARKET_CAP).asDouble());
    result.setDeltas(this.buildDeltaList(data));
    result.setDaily_volume(data.get(this.mapper.DAILY_VOLUME).asDouble());
    result.setPrice(data.get(this.mapper.CURRENT_PRICE).asDouble());
    try {
      result.setLast_update_timestamp(
          TimeFormatter.convertStringToTimestamp(data.get(this.mapper.LAST_UPDATE_DATE).asText())
              .getTime());
    } catch (ParseException ignored) {
    }
    return result;
  }

  @Override
  protected List<Price> buildPriceList(JsonNode data) {
    return null;
  }

  @Override
  protected List<Delta> buildDeltaList(JsonNode data) {
    List<Delta> result = new ArrayList<>();
    for (IntervalEnum intervalEnum :
        List.of(IntervalEnum.ONE_HOUR, IntervalEnum.ONE_DAY, IntervalEnum.SEVEN_DAY)) {
      result.add(this.buildSingleDelta(data, intervalEnum));
    }
    return result;
  }

  @Override
  protected Price buildSinglePrice(JsonNode data) {
    return null;
  }

  @Override
  protected Delta buildSingleDelta(JsonNode data, IntervalEnum delta) {
    Delta result = new Delta();
    result.setInterval(delta.name());
    double deltaPct = (data.get(this.mapper.DELTA_MAP.get(delta)).asDouble());
    result.setPct(deltaPct);
    result.setNominal(
        this.calculateNominalDelta(data.get(this.mapper.CURRENT_PRICE).asDouble(), deltaPct));
    return result;
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
