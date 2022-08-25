package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidPathAdvice {

  @ResponseBody
  @ExceptionHandler(InvalidPathException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String coinNotFoundHandler(InvalidPathException ex) {
    return ex.getMessage();
  }
}
