package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;

public abstract class ResultBuilder {

  protected Object[] requestArgs;
  protected Response response;
  protected Result result;
  protected ApiProviderEnum provider;
  protected ApiCommunicatorMethodEnum method;

  protected abstract List<Coin> buildCoinList(JsonNode data);

  protected abstract Coin buildSingleCoin(String coinName, JsonNode data);

  protected abstract Map<String, Quote> buildQuoteMap(JsonNode data);

  protected abstract Quote buildSingleQuote(JsonNode data);

  protected abstract CurrentQuote buildCurrentQuote(JsonNode data);

  protected abstract List<Price> buildPriceList(JsonNode data);

  protected abstract List<Delta> buildDeltaList(JsonNode data);

  protected abstract Price buildSinglePrice(JsonNode data);

  protected abstract Delta buildSingleDelta(JsonNode data, IntervalEnum delta);

  protected abstract List<Link> buildLinkList(JsonNode data);

  protected abstract Link buildSingleLink(JsonNode data);
}
