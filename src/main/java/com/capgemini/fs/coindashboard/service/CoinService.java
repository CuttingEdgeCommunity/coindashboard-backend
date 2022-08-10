package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.service.caching.annotation.CustomCoinCachingAnnotation;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinService {

  @CustomCoinCachingAnnotation
  Optional<String> getCoinMarketData(String name, String vs_currency);

  @CustomCoinCachingAnnotation
  Optional<String> getCoinInfo(int take, int page);

  @CustomCoinCachingAnnotation
  Optional<String> getCoinDetails(String name);

  @CustomCoinCachingAnnotation
  Optional<String> getChart(String name, long chart_from, long chart_to);
}
