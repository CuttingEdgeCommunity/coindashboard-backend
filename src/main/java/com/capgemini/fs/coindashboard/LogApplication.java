package com.capgemini.fs.coindashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class LogApplication {
  private static final Logger LOG = LoggerFactory.getLogger(LogApplication.class);

  public static void main( String[] args )
  {
    //System.out.println("Hello World!");
    SpringApplication.run(LogApplication.class, args);
    LOG.debug("This is a debug statement");
    LOG.warn("This is Warn Log");
    LOG.error("This is Error Log", new NullPointerException());
    //LOG.fatal("This is Fatal Log");
    LOG.trace("This is trace Log");

  }

  @GetMapping(value="/hello")
  public String helloWorld(){
    LOG.info("This is Info Log");
    return "Hello World!";
  }

}