package com.capgemini.fs.coindashboard.controller;

public class CoinsNotFoundException extends RuntimeException{
    public CoinsNotFoundException(String name) {
      super("Could not find employee " + name);
    }

}
