package com.capgemini.fs.coindashboard.CRUDService.mongoconfig;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import static java.util.concurrent.TimeUnit.SECONDS;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
@Log4j2
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
            .applyToConnectionPoolSettings(
                builder ->
                    builder
                        .maxWaitTime(mongoEnv.getConnection_pool_wait_time(), SECONDS)
                        .maxSize(mongoEnv.getConnection_pool_size())
                        .addConnectionPoolListener(new ConnectionPoolLogger()))
            .applyConnectionString(connectionString)
            .credential(mongoCredential)
            .build();
    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    MongoTemplate mongoTemplate = new MongoTemplate(mongo(), mongoEnv.getDatabase());
    try {
      mongoTemplate
          .indexOps(Coin.class)
          .ensureIndex(new Index().on("marketCapRank", Direction.ASC).named("rank"));
      mongoTemplate
          .indexOps(Coin.class)
          .ensureIndex(new Index().on("symbol", Direction.ASC).named("symbol"));
    } catch (Exception e) {
      log.info(e);
    }
    return mongoTemplate;
  }

  //  @Bean
  //  public ClientSession clientSession() {
  //    return mongo().startSession();
  //  }
}
