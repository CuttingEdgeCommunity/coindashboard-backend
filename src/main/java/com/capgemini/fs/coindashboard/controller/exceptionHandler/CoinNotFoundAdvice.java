package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CoinNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(CoinNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String coinNotFoundHandler(CoinNotFoundException ex) {
    return ex.getMessage();
  }
}
