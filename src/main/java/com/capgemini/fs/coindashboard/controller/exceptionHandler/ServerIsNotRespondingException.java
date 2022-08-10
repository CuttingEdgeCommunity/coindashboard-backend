package com.capgemini.fs.coindashboard.controller.exceptionHandler;

public class ServerIsNotRespondingException extends RuntimeException {
  public ServerIsNotRespondingException() {
    super("Server in not responding...");
  }
}
