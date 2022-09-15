package com.capgemini.fs.coindashboard.initializer;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
// class for initialization of a mongoDB with historical data of coins
public class MongoInit implements InitializingBean {
  @Autowired private CreateQueries createQueries;
  @Autowired private GetQueries getQueries;
  @Autowired private ApiHolder apiHolder;
  @Autowired private MongoTemplate mongoTemplate;

  @Override
  public void afterPropertiesSet() {
    log.info("Starting initialization...");
    requestingInitialData();
  }
  // method to make code cleaner
  public void requestingInitialData() {
    log.info("Requesting data...");
    final byte PAGES = 2;
    int TAKE = 500;
    List<String> symbols = new ArrayList<>();
    Optional<Result> coinMarketDataResult = Optional.empty(); // propably bad practice for optionals
    for (byte i = 0; i < PAGES; i++) {
      try {
        coinMarketDataResult =
            Optional.of(apiHolder.getTopCoins(TAKE, i, List.of("usd"))).orElse(null);
        log.info("Requested 250 topCoins from {} page", i);
        try {
          if (coinMarketDataResult.isPresent()) {
            for (int j = 0; j < TAKE; j++) {
              symbols.add(
                  coinMarketDataResult.get().getCoins().get(j).getSymbol()); // adding symbols
            }
          }
          try {
            var res = coinInfo(coinMarketDataResult,symbols.subList(i * TAKE,TAKE*(i+1)));
            passingData(res);
          } catch (Exception e) {
            log.error(e.getMessage());
          }

        } catch (Exception e) {
          log.error("Problem with symbols: {}", e.getMessage());
        }
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
  }
  // coin info wrapper
  public Optional<Result> coinInfo(Optional<Result> coinMarketDataResult, List<String> symbols) {
    log.info(symbols);
    List<Coin> coins = new ArrayList<>();
    var result = this.apiHolder.getCoinInfo(symbols);
    coinMarketDataResult.get().getCoins().sort(Comparator.comparing(Coin::getSymbol));
    result.get().getCoins().sort(Comparator.comparing(Coin::getSymbol));
    // log.info(result);
    for (int i = 0; i < symbols.size() - 1; i++) {
      Coin coin = coinMarketDataResult.get().getCoins().get(i);
      //log.info(coin);
      if (!getQueries.isCoinInDBBySymbol(symbols.get(i))) {
        try {
          Coin coinDetails = result.orElseThrow().getCoins().get(i);
          coinDetails.setMarketCapRank(coin.getMarketCapRank());
          coinDetails.setQuotes(coin.getQuotes());
          coins.add(coinDetails);
          log.info("Added coin into to 'result' {}", coin.getSymbol());
        } catch (Exception e) {
          log.error(e.getMessage());
        }
      } else {
        log.info("Coin already exists in DB {}", coin.getSymbol());
      }
    }
    result.ifPresent(value -> value.setCoins(coins));
    log.info(coins.size());
    return result;
  }
  // passer to queries
  public void passingData(Optional<Result> res) {
    log.info("Passing data...");
    try {
      res.ifPresent(result -> createQueries.CreateCoinDocuments(res.get().getCoins()));
      log.info("Successfully added initial {} topCoins data", res.get().getCoins().size());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
