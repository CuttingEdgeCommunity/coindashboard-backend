package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.caching.annotation.CustomCoinCachingAnnotation;
import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinService {
  ArrayList<Coin> getCoins();

  Coin getCoinById(Integer id);

  @CustomCoinCachingAnnotation
  Optional<Coin> getCoinByName(String name);
}
