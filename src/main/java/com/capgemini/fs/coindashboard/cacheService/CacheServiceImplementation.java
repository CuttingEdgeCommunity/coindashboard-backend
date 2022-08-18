package com.capgemini.fs.coindashboard.cacheService;

import com.capgemini.fs.coindashboard.CRUDService.queries.Queries;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheServiceImplementation implements CacheService {
  private static final Logger LOG = LogManager.getLogger(CacheServiceImplementation.class);
  @Autowired Queries queries;

  @Override
  @Cacheable(cacheNames = "getCoinMarketData")
  public Optional<String> getCoinMarketData(String name, String vs_currency) {
    return Optional.of(queries.getCoinMarketData(name, vs_currency));
  }

  @Override
  @Cacheable(cacheNames = "getCoinInfo")
  public Optional<String> getCoinInfo(int take, int page) {
    return Optional.empty();
  }

  @Override
  @Cacheable(cacheNames = "getCoinDetails")
  public Optional<String> getCoinDetails(String name) {
    return Optional.empty();
  }

  @Override
  @Cacheable(cacheNames = "getChart")
  public Optional<String> getChart(String name, long chart_from, long chart_to) {
    return Optional.empty();
  }
}
