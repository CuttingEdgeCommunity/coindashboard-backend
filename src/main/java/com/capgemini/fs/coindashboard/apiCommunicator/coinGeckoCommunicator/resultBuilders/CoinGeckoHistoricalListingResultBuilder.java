package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
class CoinGeckoHistoricalListingResultBuilder extends CoinGeckoMarketDataBuilderBaseClass{
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.HISTORICAL_LISTING;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    return Collections.emptyList();
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    return null;
  }

  @Override
  protected Map<String, Quote> buildQuoteMap(JsonNode data) {
    return Collections.emptyMap();
  }

  @Override
  protected CurrentQuote buildCurrentQuote(JsonNode data) {
    return null;
  }

  @Override
  protected List<Price> buildPriceList(JsonNode data) {
    return Collections.emptyList();
  }

  @Override
  protected Price buildSinglePrice(JsonNode data) {
    return null;
  }
}
