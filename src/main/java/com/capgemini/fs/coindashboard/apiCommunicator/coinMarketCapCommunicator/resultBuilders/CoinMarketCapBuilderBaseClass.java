package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.IResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public abstract class CoinMarketCapBuilderBaseClass extends ResultBuilder
    implements IResultBuilder {
  @Autowired protected CoinMarketCapFieldNameMapper mapper;
  protected String errorMessage;

  @PostConstruct
  public void init() {
    this.provider = ApiProviderEnum.COIN_MARKET_CAP;
  }

  protected String parseStatus(JsonNode status) {
    if (status.get(this.mapper.ERROR_CODE).asInt() != 0) {
      return status.get(this.mapper.ERROR_MESSAGE).asText();
    }
    return null;
  }

  @Override
  public void setData(Response response, Object... requestArgs) {
    this.response = response;
    this.requestArgs = requestArgs;
  }

  @Override
  public void reset() {
    this.result = new Result();
    this.errorMessage = null;
  }

  @Override
  public void setResultProvider() {
    this.result.setProvider(this.provider);
  }

  public FieldNameMapper getMapper() {
    return this.mapper;
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
    if (this.result.getStatus() == ResultStatus.SUCCESS
        && this.result.getCoins() != null
        && ((List<?>) this.requestArgs[0]).size() > this.result.getCoins().size()) {
      this.result.setStatus(ResultStatus.PARTIAL_SUCCESS);
      String differences =
          ((List<String>) this.requestArgs[0])
              .stream()
                  .filter(
                      element ->
                          this.result.getCoins().stream()
                              .noneMatch(c -> Objects.equals(c.getSymbol(), element)))
                  .collect(Collectors.joining(","));
      this.errorMessage = "coins not found: " + differences;
    }
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    ArrayList<Coin> result = new ArrayList<>();
    ObjectMapper objMapper = new ObjectMapper();
    Map<String, ObjectNode> responseBodyConverted =
        objMapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, ObjectNode> coin : responseBodyConverted.entrySet()) {
      if (coin.getValue() == null || coin.getValue().size() == 0) {
        continue;
      }
      result.add(
          this.buildSingleCoin(coin.getValue().get(this.mapper.NAME).asText(), coin.getValue()));
    }
    return result;
  }

  @Override
  public void setErrorMessage() {
    this.result.setErrorMessage(this.errorMessage);
  }

  @Override
  public Result getResult() {
    return this.result;
  }

  @Override
  public void setCoins() {
    this.result.setCoins(this.buildCoinList(this.response.getResponseBody().get(this.mapper.DATA)));
  }
}
