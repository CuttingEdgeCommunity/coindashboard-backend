package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.IResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
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
    if (response.getResponseCode() != HttpStatus.OK.value()) {
      this.result.setStatus(ResultStatus.FAILURE);
      this.errorMessage =
          response.getResponseBody().get(this.mapper.STATUS_NOT_OK_ERROR_MESSAGE).asText();
    } else {
      String statusError =
          this.parseStatus(this.response.getResponseBody().get(this.mapper.STATUS));
      if (statusError != null) {
        this.result.setStatus(ResultStatus.FAILURE);
        this.errorMessage = statusError;
      } else this.result.setStatus(ResultStatus.SUCCESS);
    }
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
