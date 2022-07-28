package com.capgemini.fs.coindashboard.controller;

public class CoinNotFoundException extends RuntimeException {
  public CoinNotFoundException(String name) {

    super("Could not find employee " + name);
  }
}
