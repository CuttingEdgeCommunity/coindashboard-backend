package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.caching.annotation.CustomCoinCachingAnnotation;
import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CoinServiceImplementation implements CoinService,CoinDateRetriever {
  private static final Logger LOG = LogManager.getLogger(CoinServiceImplementation.class);
  private ArrayList<Coin> coins = new ArrayList<>();

  @Override
  @Cacheable("coins")
  public ArrayList<Coin> getCoins() {
    retrieveDataFromDatabase();
    LOG.info("Getting coins from database...");
    return coins;
  }

  @Override
  @CustomCoinCachingAnnotation
  public Coin getCoinById(Integer id) {
    CoinDateRetriever.retrieveDataFromDatabase(coins);
    LOG.info("Getting coins from database...");
    return coins.get(id);
  }

}
