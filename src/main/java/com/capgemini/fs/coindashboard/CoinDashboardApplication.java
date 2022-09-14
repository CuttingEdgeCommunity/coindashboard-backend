package com.capgemini.fs.coindashboard;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan
@EnableScheduling
@OpenAPIDefinition(
    info = @Info(title = "API Documentation", version = "1.1", description = "Finally working"))
public class CoinDashboardApplication {

  public static void main(String[] args) {
    final String env = System.getenv("COINDASHBOARD_RUNTIME_ENVIRONMENT");
    final String activeProfile = (env != null && env.equals("DOCKER")) ? "docker" : "host";
    new SpringApplicationBuilder(CoinDashboardApplication.class).profiles(activeProfile).run(args);
  }
}
