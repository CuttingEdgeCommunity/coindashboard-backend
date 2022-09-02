package com.capgemini.fs.coindashboard.initializer;

import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
// class for initialization of a mongoDB with historical data of coins
public class MongoInit implements InitializingBean {
  @Autowired private CreateQueries createQueries;
  @Autowired private GetQueries getQueries;
  @Autowired private ApiHolder apiHolder;

  @Override
  public void afterPropertiesSet() {
    log.info("Starting initialization...");
    final byte PAGES = 4;
    for (byte i = 0; PAGES > i; i++) {
      Optional<Result> coinMarketDataResult;
      try {
        coinMarketDataResult =
            Optional.of(apiHolder.getTopCoins(250, i, List.of("usd"))).orElse(null);
        log.info("Requested 250 coins from API");
      } catch (Exception e) {
        log.error(e.getMessage());
        return;
      }
      try {
        coinMarketDataResult.ifPresent(
            result -> createQueries.CreateCoinDocuments(result.getCoins()));
        log.info("Successfully added initial 250 topCoins data from {} page", i);
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
  }
}
