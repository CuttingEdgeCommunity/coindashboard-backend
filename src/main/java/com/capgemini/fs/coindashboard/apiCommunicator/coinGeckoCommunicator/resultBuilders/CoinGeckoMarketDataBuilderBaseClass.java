package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;

public abstract class CoinGeckoMarketDataBuilderBaseClass extends CoinGeckoBuilderBaseClass {
  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    return null;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    return null;
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
