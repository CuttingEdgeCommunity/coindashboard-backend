package com.capgemini.fs.coindashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(
    scanBasePackages = {
      "com.capgemini.fs.coindashboard.controller",
      "com.capgemini.fs.coindashboard.database",
      "com.capgemini.fs.coindashboard.initService",
      "com.capgemini.fs.coindashboard.CRUDService",
      "com.capgemini.fs.coindashboard.apiCommunicator",
      "com.capgemini.fs.coindashboard.updatingService"
    })
@EnableCaching
public class CoinDashboardApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoinDashboardApplication.class, args);
  }
}
