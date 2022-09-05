package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapCoinInfoResultBuilder extends CoinMarketCapBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.COIN_INFO;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = new Coin();
    result.setName(data.get(this.mapper.NAME).asText().toLowerCase());
    result.setSymbol(data.get(this.mapper.SYMBOL).asText().toLowerCase());
    result.setDescription(data.get(this.mapper.DESCRIPTION).asText());
    try {
      result.setGenesis_date(
          TimeFormatter.convertStringToTimestamp(data.get(this.mapper.GENESIS_DATE).asText())
              .getTime());
    } catch (ParseException ignored) {
    }
    result.setImage_url(data.get(this.mapper.IMAGE_URL).asText());
    String contractAddress = this.parseToken(data.get(this.mapper.PLATFORM));
    if (contractAddress != null) {
      result.setIs_token(true);
      result.setContract_address(contractAddress);
    }
    result.setLinks(this.buildLinkList(data.get(this.mapper.LINKS)));
    return result;
  }

  private String parseToken(JsonNode data) {
    if (data == null || data.size() == 0) return null;
    return data.get(this.mapper.TOKEN_ADDRESS).asText();
  }

  @Override
  protected List<Link> buildLinkList(JsonNode data) {
    ArrayList<Link> result = new ArrayList<>();
    ObjectMapper objMapper = new ObjectMapper();
    Map<String, ArrayNode> responseBodyConverted =
        objMapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, ArrayNode> url : responseBodyConverted.entrySet()) {
      if (url.getValue().size() == 0) continue;
      Link link = buildSingleLink(url.getValue());
      link.setTitle(url.getKey());
      result.add(link);
    }
    return result;
  }

  @Override
  protected Link buildSingleLink(JsonNode data) {
    Link result = new Link();
    result.setUrl(data.get(0).asText());
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
}
