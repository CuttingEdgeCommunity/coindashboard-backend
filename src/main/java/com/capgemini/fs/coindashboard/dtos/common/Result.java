package com.capgemini.fs.coindashboard.dtos.common;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import java.util.Objects;
import lombok.Data;

@Data
public class Result {

  private ApiProviderEnum provider;
  private ResultStatus status;
  private String errorMessage;

  public Result() {}

  public Result(ApiProviderEnum provider, ResultStatus status, String errorMessage) {
    this.provider = provider;
    this.status = status;
    this.errorMessage = errorMessage;
  }

  public void setStatus(ResultStatus status) {
    this.status = status;
  }

  public void setStatus(boolean isPartialSuccess) {
    if (!Objects.equals(this.getErrorMessage(), "") && this.getErrorMessage() != null) {
      this.setStatus(ResultStatus.FAILURE);
    } else if (isPartialSuccess) {
      this.setStatus(ResultStatus.PARTIAL_SUCCESS);
    } else {
      this.setStatus(ResultStatus.SUCCESS);
    }
  }
}
