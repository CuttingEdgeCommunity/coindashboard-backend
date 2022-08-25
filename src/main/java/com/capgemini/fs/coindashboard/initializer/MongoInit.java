package com.capgemini.fs.coindashboard.initializer;

import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

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
    Result coinMarketDataResult;
    try {
      coinMarketDataResult = apiHolder.getCoinMarketData(List.of("btc"), List.of("usd"));
    } catch (Exception e) {
      log.error(e.getMessage());
      return;
    }
    if (coinMarketDataResult != null) {
      try {
        createQueries.CreateCoinDocument(coinMarketDataResult.getCoins().get(0));
        log.info("Successfully added initial btc data");
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    } else {
      log.info("Data not loaded from the APIHolder");
    }
  }
}
