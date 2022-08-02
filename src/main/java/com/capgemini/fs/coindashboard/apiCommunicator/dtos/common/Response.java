package com.capgemini.fs.coindashboard.apiCommunicator.dtos.common;

import lombok.Data;
import java.util.Objects;

@Data
public class Response {
  private ResponseStatus status;
  private String errorMessage;
}
