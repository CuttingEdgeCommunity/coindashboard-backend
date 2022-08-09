package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.service.caching.annotation.CustomCoinCachingAnnotation;
import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import java.util.ArrayList;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CoinServiceImplementation implements CoinService, CoinDataRetriever {
  private static final Logger LOG = LogManager.getLogger(CoinServiceImplementation.class);
  private ArrayList<Coin> coins = new ArrayList<>();

  @Override
  @Cacheable("coins")
  public ArrayList<Coin> getCoins() {
    coins = CoinDataRetriever.getCoinData();
    LOG.info("Getting coins from database...");
    return coins;
  }

  @Override
  public Coin getCoinById(Integer id) {
    return null;
  }

  @Override
  @CustomCoinCachingAnnotation
  public Optional<Coin> getCoinByName(String name) {
    coins = CoinDataRetriever.getCoinData();
    LOG.info("Getting coins from database...");
    for (Coin coin : coins) {
      if (coin.getName().equals(name)) {
        return Optional.of(coin);
      }
    }
    return Optional.empty();
  }
}
