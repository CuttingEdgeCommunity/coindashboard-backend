package com.capgemini.fs.coindashboard.dtos.common;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

  private ApiProviderEnum provider;
  private ResultStatus status;
  private String errorMessage;

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
