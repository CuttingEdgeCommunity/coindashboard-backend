package com.capgemini.fs.coindashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
    scanBasePackages = {
      "com.capgemini.fs.coindashboard.controller",
      "com.capgemini.fs.coindashboard.database",
      "com.capgemini.fs.coindashboard.initService",
      "com.capgemini.fs.coindashboard.CRUDService",
      "com.capgemini.fs.coindashboard.apiCommunicator",
      "com.capgemini.fs.coindashboard.updatingService"
    })
@EnableScheduling
@EnableCaching
public class CoinDashboardApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoinDashboardApplication.class, args);
  }
}
