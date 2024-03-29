package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CoinNotFoundException extends RuntimeException {
  public CoinNotFoundException(String name) {
    super("Could not find coin " + name);
  }
}
