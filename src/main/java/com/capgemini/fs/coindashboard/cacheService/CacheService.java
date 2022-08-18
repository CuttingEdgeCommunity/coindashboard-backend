package com.capgemini.fs.coindashboard.cacheService;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheService {

  Optional<String> getCoinMarketData(String name, String vs_currency);

  Optional<String> getCoinInfo(int take, int page);

  Optional<String> getCoinDetails(String name);

  Optional<String> getChart(String name, long chart_from, long chart_to);
}
