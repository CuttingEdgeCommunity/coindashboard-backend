package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingAdvice {

  @ResponseBody
  @ExceptionHandler(CoinNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String coinNotFoundHandler(CoinNotFoundException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(InvalidPathException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String coinNotFoundHandler(InvalidPathException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(ServerIsNotRespondingException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  String ServerNotRespondingHandler(ServerIsNotRespondingException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    return new ResponseEntity<>(
        "not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
