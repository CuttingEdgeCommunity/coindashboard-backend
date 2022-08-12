package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.service.caching.annotation.CustomCoinCachingAnnotation;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CoinServiceImplementation implements CoinService {
  private static final Logger LOG = LogManager.getLogger(CoinServiceImplementation.class);

  @Override
  @CustomCoinCachingAnnotation
  public Optional<String> getCoinMarketData(String name, String vs_currency) {
    return Optional.empty();
  }

  @Override
  @CustomCoinCachingAnnotation
  public Optional<String> getCoinInfo(int take, int page) {
    return Optional.empty();
  }

  @Override
  @CustomCoinCachingAnnotation
  public Optional<String> getCoinDetails(String name) {
    return Optional.empty();
  }

  @Override
  @CustomCoinCachingAnnotation
  public Optional<String> getChart(String name, long chart_from, long chart_to) {
    return Optional.empty();
  }
}
