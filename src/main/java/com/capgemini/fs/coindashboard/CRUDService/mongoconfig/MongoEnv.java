package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MongoEnv.class)
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Getter
@Setter
public class MongoEnv {
  private String host;
  private String port;
  private String username;
  private String password;
  private String database;
}
