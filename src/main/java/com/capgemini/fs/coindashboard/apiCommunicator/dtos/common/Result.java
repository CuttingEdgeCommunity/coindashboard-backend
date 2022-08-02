package com.capgemini.fs.coindashboard.apiCommunicator.dtos.common;

import lombok.Data;

@Data
public class Result {

  private ResultStatus status;
  private String errorMessage;
}
