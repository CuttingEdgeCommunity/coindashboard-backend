package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapTopCoinsResultBuilder extends CoinMarketCapMarketDataBuilderBaseClass {

  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.TOP_COINS;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected Coin buildSingleCoin(String coinName, JsonNode data) {
    Coin result = super.buildSingleCoin(coinName, data);
    result.setMarketCapRank(data.get(this.mapper.MARKET_CAP_RANK).asInt());
    result.setQuotes(this.buildQuoteMap(data.get(this.mapper.QUOTE)));
    return result;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    ArrayList<Coin> result = new ArrayList<>();
    ObjectMapper objMapper = new ObjectMapper();
    List<JsonNode> responseBodyConverted = objMapper.convertValue(data, new TypeReference<>() {});
    for (JsonNode coin : responseBodyConverted) {
      if (coin == null || coin.size() == 0) {
        continue;
      }
      result.add(this.buildSingleCoin(coin.get(this.mapper.NAME).asText(), coin));
    }
    return result;
  }

  @Override
  public void setResultStatus() {
    if (this.errorMessage != null) this.result.setStatus(ResultStatus.FAILURE);
    if (response.getResponseCode() == HttpStatus.NOT_FOUND.value()) {
      this.result.setStatus(ResultStatus.FAILURE);
      this.errorMessage = response.getResponseBody().get(this.mapper.NOT_FOUND_MESSAGE).asText();
    } else {
      String statusError =
          this.parseStatus(this.response.getResponseBody().get(this.mapper.STATUS));
      if (statusError != null) {
        this.result.setStatus(ResultStatus.FAILURE);
        this.errorMessage = statusError;
      } else this.result.setStatus(ResultStatus.SUCCESS);
    }
  }
}
