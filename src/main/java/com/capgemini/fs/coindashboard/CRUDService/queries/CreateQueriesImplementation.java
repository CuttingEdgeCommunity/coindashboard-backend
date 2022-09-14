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
      return true;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  @Override
  public boolean createCoinDocumentWithUpdatingDetails(Coin coin) {
    List<String> symbols = List.of(coin.getSymbol());
    var resultCoinInfo = this.apiHolder.getCoinInfo(symbols);
    Coin coinWithDetails = resultCoinInfo.orElseThrow().getCoins().get(0);
    coin.setContract_address(coinWithDetails.getContract_address());
    coin.setDescription(coinWithDetails.getDescription());
    coin.setGenesis_date(coinWithDetails.getGenesis_date());
    coin.setImage_url(coinWithDetails.getImage_url());
    coin.setIs_token(coinWithDetails.getIs_token());
    coin.setLinks(coinWithDetails.getLinks());
    createCoinDocument(coin);
    log.info("new coin (" + coin.getSymbol() + ") in the database.");
    return true;
  }

  @Override
  public void createCoinDocuments(List<Coin> coins) {
    try {
      mongoTemplate.insertAll(coins);
      log.info("insert performed...");
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
  }
}
