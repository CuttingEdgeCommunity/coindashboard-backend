package com.capgemini.fs.coindashboard;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {
    "com.capgemini.fs.coindashboard.apiCommunicator"})
@ComponentScan(basePackages = "com.capgemini.fs.coindashboard.apiCommunicator")
public class CoinDashboardApplication implements CommandLineRunner {

  @Autowired
  private ApiHolder apiHolder;

  public static void main(String[] args) {
    SpringApplication.run(CoinDashboardApplication.class, args);
  }

  @Override
  public void run(String... args) {
    String coin = "sadfasddf";
    var results = apiHolder.getCoinMarketData(coin);
    for (CoinMarketDataResult result :
        results) {
      System.out.println("Provider: " + result.getProvider());
      System.out.println("Status: " + result.getStatus());
      System.out.println("ErrorMessage: " + result.getErrorMessage());
      System.out.println("Data: " + result.getCoinMarketDataDTOS());
    }
    results = apiHolder.getHistoricalCoinMarketData(coin, 1659945117553L);
    System.out.println("=====history=====");
    for (CoinMarketDataResult result :
        results) {
      System.out.println("Provider: " + result.getProvider());
      System.out.println("Status: " + result.getStatus());
      System.out.println("ErrorMessage: " + result.getErrorMessage());
      System.out.println("Data: " + result.getCoinMarketDataDTOS());
    }
  }
}
