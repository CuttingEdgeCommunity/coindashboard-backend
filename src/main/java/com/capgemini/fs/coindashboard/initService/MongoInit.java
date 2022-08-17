package com.capgemini.fs.coindashboard.initService;

import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
// class for initialization of a mongoDB with historical data of coins
public class MongoInit implements CommandLineRunner {
  private static final Logger log = LogManager.getLogger(MongoInit.class);
  @Autowired private CreateQueries createQueries;
  @Autowired private ApiHolder apiHolder;

  @Override
  public void run(String... args) {
    log.info("Starting initialization...");
    CoinMarketDataResult coinMarketDataResult;
    try {
      coinMarketDataResult = apiHolder.getCoinMarketData("btc");
    } catch (Exception e) {
      log.error(e.getMessage());
      return;
    }
    if (coinMarketDataResult != null) {
      createQueries.CreateCoinDocument(coinMarketDataResult.getCoinMarketDataDTOS().get(0));
    } else {
      log.info("Data not loaded from the APIHolder");
    }
  }
}
