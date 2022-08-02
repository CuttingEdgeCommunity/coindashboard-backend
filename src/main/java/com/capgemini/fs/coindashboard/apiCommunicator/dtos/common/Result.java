package com.capgemini.fs.coindashboard.apiCommunicator.dtos.common;

import lombok.Data;

@Data
public class Result {

  private ResultStatus status;
  private String errorMessage;

  public Result() {
  }

  public Result(ResultStatus status, String errorMessage) {
    this.status = status;
    this.errorMessage = errorMessage;
  }

}
