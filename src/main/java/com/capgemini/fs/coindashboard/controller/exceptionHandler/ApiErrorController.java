package com.capgemini.fs.coindashboard.controller.exceptionHandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
public class ApiErrorController implements ErrorController {

  @RequestMapping("error")
  public ResponseEntity<String> handleError() {
    log.warn("call with incorrect mapping made");
    throw new InvalidPathException();
  }
}
