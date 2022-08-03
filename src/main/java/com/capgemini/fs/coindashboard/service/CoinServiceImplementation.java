package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.caching.annotation.CustomCoinCachingAnnotation;
import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CoinServiceImplementation implements CoinService {
  private static final Logger LOG = LogManager.getLogger(CoinServiceImplementation.class);
  private ArrayList<Coin> coins = new ArrayList<>();

  @Override
  @Cacheable("coins")
  public ArrayList<Coin> getCoins() {
    retrieveDataFromDatabase();
    LOG.info("Getting coins from database...");
    return coins;
  }

  private void createCoinData(ArrayList<Coin> coins) {
    // coins.add(Coin.builder().id(1).name("Bitcoin"))
  }

  @Override
  @CustomCoinCachingAnnotation
  public Coin getCoinById(Integer id) {
    retrieveDataFromDatabase();
    LOG.info("Getting coins from database...");
    return coins.get(id);
  }

  private void retrieveDataFromDatabase() {
    try {
      createCoinData(coins);
      LOG.info("Sleep for 4 sec to make impression of a real server...");
      Thread.sleep(4000);
    } catch (InterruptedException ex) {
      LOG.error(ex.getMessage());
    }
  }
}
