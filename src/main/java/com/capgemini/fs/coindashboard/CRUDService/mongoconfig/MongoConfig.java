package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
  @Autowired private MongoEnv mongoEnv;

  @Bean
  public MongoClient mongo() {
    MongoCredential mongoCredential =
        MongoCredential.createCredential(
            mongoEnv.getUsername(), mongoEnv.getDatabase(), mongoEnv.getPassword().toCharArray());
    ConnectionString connectionString =
        new ConnectionString(
            String.format(
                "mongodb://%s:%s/%s",
                mongoEnv.getHost(), mongoEnv.getPort(), mongoEnv.getPassword()));
    MongoClientSettings mongoClientSettings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .credential(mongoCredential)
            .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongo(), mongoEnv.getDatabase());
  }
}
