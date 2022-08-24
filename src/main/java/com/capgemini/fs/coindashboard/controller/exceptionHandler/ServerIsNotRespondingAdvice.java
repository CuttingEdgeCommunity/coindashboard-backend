package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServerIsNotRespondingAdvice {
  @ResponseBody
  @ExceptionHandler(ServerIsNotRespondingException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  String ServerNotRespondingHandler(ServerIsNotRespondingException ex) {
    return ex.getMessage();
  }
}
