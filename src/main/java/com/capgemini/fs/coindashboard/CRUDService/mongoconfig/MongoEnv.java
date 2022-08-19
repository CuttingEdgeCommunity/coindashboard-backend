package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MongoEnv.class)
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoEnv {
  private String host;
  private String port;
  private String username;
  private String password;
  private String database;

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getDatabase() {
    return database;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setDatabase(String database) {
    this.database = database;
  }
}
