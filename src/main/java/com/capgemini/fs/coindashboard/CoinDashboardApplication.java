package com.capgemini.fs.coindashboard;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
    scanBasePackages = {
      "com.capgemini.fs.coindashboard.controller",
      "com.capgemini.fs.coindashboard.database",
      "com.capgemini.fs.coindashboard.initializer",
      "com.capgemini.fs.coindashboard.CRUDService",
      "com.capgemini.fs.coindashboard.apiCommunicator",
      "com.capgemini.fs.coindashboard.updatingService",
      "com.capgemini.fs.coindashboard.cacheService"
    })
@EnableScheduling
@OpenAPIDefinition(
    info = @Info(title = "API Documentation", version = "1.1", description = "Finally working"))
public class CoinDashboardApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoinDashboardApplication.class, args);
  }
}
