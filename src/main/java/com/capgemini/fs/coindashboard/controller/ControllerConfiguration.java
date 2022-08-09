package com.capgemini.fs.coindashboard.controller;

import com.capgemini.fs.coindashboard.service.CoinService;
import com.capgemini.fs.coindashboard.service.CoinServiceImplementation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// https://stackoverflow.com/questions/27587392/how-to-create-a-bean-as-instance-of-interface-class

@Configuration
@EnableCaching
public class ControllerConfiguration {

  private static final Logger log = LogManager.getLogger(ControllerConfiguration.class);

  // place where we should load cache from communicator branch
  @Bean(name = "coinServiceBean")
  public CoinService createBean() {
    CoinService bean = new CoinServiceImplementation();
    return bean;
  }
}
