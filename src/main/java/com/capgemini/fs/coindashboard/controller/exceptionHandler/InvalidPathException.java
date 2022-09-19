package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPathException extends RuntimeException {

  public InvalidPathException() {
    super(
        "{ \"path is invalid try\":"
            + "\"{\\\"links\\\":{\\\"coinInfo\\\":\\\"/api/coins\\\",\\\"coinDetails\\\":\\\"/api/coins/{name}\\\",\\\"chart\\\":\\\"/api/coins/{name}/chart\\\",\\\"marketData\\\":\\\"api/coins/{name}/marketdata\\\",\\\"search\\\":\\\"api/coins/find/{regex}\\\"}}\""
            + "}");
  }
}
