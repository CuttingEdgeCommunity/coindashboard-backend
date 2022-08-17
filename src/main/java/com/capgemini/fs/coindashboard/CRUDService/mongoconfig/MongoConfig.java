package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

  @Bean
  public MongoClient mongo() {
    MongoCredential mongoCredential =
        MongoCredential.createCredential("root", "coindashboard", "mongo".toCharArray());
    ConnectionString connectionString =
        new ConnectionString("mongodb://localhost:27017/coindashboard");
    MongoClientSettings mongoClientSettings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .credential(mongoCredential)
            .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongo(), "coindashboard");
  }
}
