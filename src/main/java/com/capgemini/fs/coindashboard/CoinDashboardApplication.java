package com.capgemini.fs.coindashboard;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "com.capgemini.fs.coindashboard.API_documentation",
    })
public class CoinDashboardApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoinDashboardApplication.class, args);
  }
}
