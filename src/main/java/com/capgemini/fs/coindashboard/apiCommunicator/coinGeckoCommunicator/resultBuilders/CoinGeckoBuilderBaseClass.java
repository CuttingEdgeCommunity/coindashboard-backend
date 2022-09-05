package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.CoinGeckoFieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.IResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public abstract class CoinGeckoBuilderBaseClass extends ResultBuilder implements IResultBuilder {
  @Autowired protected CoinGeckoFieldNameMapper mapper;
  protected String errorMessage;

  @PostConstruct
  public void init() {
    this.provider = ApiProviderEnum.COIN_GECKO;
  }

  protected String parseStatus(JsonNode status) {
    if (status.get(this.mapper.ERROR) != null) {
      return status.get(this.mapper.ERROR).asText();
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
      this.errorMessage = response.getResponseBody().get(this.mapper.ERROR).asText();
    } else this.result.setStatus(ResultStatus.SUCCESS);
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    return null;
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
    this.result.setCoins(this.buildCoinList(this.response.getResponseBody()));
  }
}
