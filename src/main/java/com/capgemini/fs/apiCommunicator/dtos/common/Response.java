package com.capgemini.fs.apiCommunicator.dtos.common;

import java.util.Objects;

public class Response {

  private final ResponseStatus status;
  private final String errorMessage;

  public Response(ResponseStatus status, String errorMessage) {
    this.status = status;
    this.errorMessage = errorMessage;
  }

  public ResponseStatus status() {
    return status;
  }

  public String errorMessage() {
    return errorMessage;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (Response) obj;
    return Objects.equals(this.status, that.status) &&
        Objects.equals(this.errorMessage, that.errorMessage);
  }

}
