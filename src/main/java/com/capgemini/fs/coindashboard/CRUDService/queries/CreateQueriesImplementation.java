package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CreateQueriesImplementation implements CreateQueries {

  @Autowired MongoTemplate mongoTemplate;

  @Override
  public boolean CreateCoinDocument(Coin coin) {
    try {
      mongoTemplate.save(coin, "Coin");
      return true;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  @Override
  public void CreateCoinDocuments(List<Coin> coins) {
    try {
      mongoTemplate.insertAll(coins);
      log.info("insert performed...");
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
  }
}
