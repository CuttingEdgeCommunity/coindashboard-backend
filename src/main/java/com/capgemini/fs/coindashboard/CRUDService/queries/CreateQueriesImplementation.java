package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CreateQueriesImplementation implements CreateQueries {

  @Autowired MongoTemplate mongoTemplate;
  @Autowired private ApiHolder apiHolder;

  @Override
  public boolean createCoinDocument(Coin coin) {
    try {
      mongoTemplate.save(coin, "Coin");
      log.info("Coin {} has been successfully inserted into the database.", coin.getName());
      return true;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  @Override
  public void createCoinDocuments(List<Coin> coins) {
    try {
      log.info("Insert of {} coins has started...", coins.size());
      mongoTemplate.insertAll(coins);
      log.info("Insert of {} coins has been completed.", coins.size());
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
  }
}
