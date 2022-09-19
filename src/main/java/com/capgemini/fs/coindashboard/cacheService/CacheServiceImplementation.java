package com.capgemini.fs.coindashboard.cacheService;

import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CacheServiceImplementation implements CacheService {

  @Autowired GetQueries getQueries;

  @Override
  @Cacheable("getCoinMarketData")
  public Optional<String> getCoinMarketData(String name, String vs_currency) {
    return Optional.of(queries.getCoinMarketData(name, vs_currency));

  @Override
  @Cacheable("getCoinInfo")
  public Optional<String> getCoinInfo(int take, int page) {
    return Optional.of(getQueries.getCoins(take, page));
  }

  @Override
  @Cacheable("getCoinDetails")
  public Optional<String> getCoinDetails(String name) {
    throw new UnsupportedOperationException("Method not yet implemented.");
  }

  @Override
  @Cacheable("getChart")
  public Optional<String> getChart(String name, long chart_from, long chart_to) {
    throw new UnsupportedOperationException("Method not yet implemented.");
  }

  @Override
  @Cacheable("getCoinByRegex")
  public Optional<String> getCoinByRegex(String query) {
    return Optional.of(getQueries.findCoinByRegex(query));
  }
}
