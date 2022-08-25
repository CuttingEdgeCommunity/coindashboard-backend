package com.capgemini.fs.coindashboard.cacheService;

import com.capgemini.fs.coindashboard.CRUDService.queries.Queries;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CacheServiceImplementation implements CacheService {

  @Autowired Queries queries;

  @Override
  @Cacheable(cacheNames = "getCoinMarketData")
  public Optional<String> getCoinMarketData(String name, String vs_currency) {
    log.info("Getting coin market data {},{}", name, vs_currency);
    return Optional.of(queries.getCoinMarketData(name, vs_currency));
  }

  @Override
  @Cacheable(cacheNames = "getCoinInfo")
  public Optional<String> getCoinInfo(int take, int page) {
    log.info("Getting coin info:{},{}", take, page);
    throw new UnsupportedOperationException("Method not yet implemented.");
  }

  @Override
  @Cacheable(cacheNames = "getCoinDetails")
  public Optional<String> getCoinDetails(String name) {
    log.info("Getting coin details by name:{}", name);
    throw new UnsupportedOperationException("Method not yet implemented.");
  }

  @Override
  @Cacheable(cacheNames = "getChart")
  public Optional<String> getChart(String name, long chart_from, long chart_to) {
    log.info("Getting chart by name:{}", name);
    throw new UnsupportedOperationException("Method not yet implemented.");
  }
}
