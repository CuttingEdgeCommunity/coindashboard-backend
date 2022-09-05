package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.fasterxml.jackson.databind.JsonNode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoCoinInfoResultBuilder extends CoinGeckoBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.COIN_INFO;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    return List.of(this.buildSingleCoin(data.get(this.mapper.NAME).asText().toLowerCase(), data));
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = new Coin();
    result.setName(data.get(this.mapper.NAME).asText().toLowerCase());
    result.setSymbol(data.get(this.mapper.SYMBOL).asText().toLowerCase());
    result.setDescription(data.get(this.mapper.DESCRIPTION).get(this.mapper.LANGUAGE).asText());
    try {
      result.setGenesis_date(
          TimeFormatter.convertStringToTimestamp(data.get(this.mapper.GENESIS_DATE).asText())
              .getTime());
    } catch (ParseException ignored) {
    }
    result.setImage_url(data.get(this.mapper.IMAGE_URL).get(this.mapper.IMAGE_TYPE).asText());
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

  // TODO links
  @Override
  protected List<Link> buildLinkList(JsonNode data) {
    ArrayList<Link> result = new ArrayList<>();
    Link link = buildSingleLink(data.get("homepage"));
    link.setTitle("homepage");
    result.add(link);
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
