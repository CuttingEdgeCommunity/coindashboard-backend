package com.capgemini.fs.coindashboard.apiCommunicator.dtos.common;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import lombok.Data;

@Data
public class Result {

  private ApiProviderEnum provider;
  private ResultStatus status;
  private String errorMessage;

  public Result() {
  }

  public Result(ApiProviderEnum provider, ResultStatus status, String errorMessage) {
    this.provider = provider;
    this.status = status;
    this.errorMessage = errorMessage;
  }

}
