package com.capgemini.fs.coindashboard.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;

@Configuration
public class LoadDatabase {

  private static final Logger log = LogManager.getLogger(LoadDatabase.class);


  @Bean
  CommandLineRunner initDatabase(CoinsRepository repository) {
    ArrayList<MarketCapAndTime> firstCoin = new ArrayList<>();
    firstCoin.add(new MarketCapAndTime(12L, 12.5));
    return args -> {
      log.info("Preloading " + repository.save(new Coins("SC", firstCoin)));
      log.info("Preloading " + repository.save(new Coins("SC", firstCoin)));
      log.info("Preloading " + repository.save(new Coins("SC", firstCoin)));
      log.info("Preloading " + repository.save(new Coins("SC", firstCoin)));
      log.info("Preloading " + repository.save(new Coins("SC", firstCoin)));
      log.info("Loading"+1L);

    };
  }
}