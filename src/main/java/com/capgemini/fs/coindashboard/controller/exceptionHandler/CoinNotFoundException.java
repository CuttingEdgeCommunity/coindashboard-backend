package com.capgemini.fs.coindashboard.controller.exceptionHandler;

public class CoinNotFoundException extends RuntimeException {
  public CoinNotFoundException(String name) {
    super("Could not find coin " + name);
  }
}
