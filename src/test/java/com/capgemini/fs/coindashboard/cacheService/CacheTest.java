package com.capgemini.fs.coindashboard.cacheService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.capgemini.fs.coindashboard.CRUDService.queries.Queries;
import com.capgemini.fs.coindashboard.cacheService.cacheConfig.CacheConfig;
import com.github.benmanes.caffeine.cache.Ticker;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CacheConfig.class, CacheServiceImplementation.class})
@AutoConfigureMockMvc
public class CacheTest {
  private static final String name = "Bitcoin";
  private static final String vs_currency = "usd";
  @MockBean private CacheService cacheService;
  @MockBean CacheServiceImplementation cacheServiceImplementation;
  @MockBean Queries queries;

  @Configuration
  public static class TestConfig {
    // fakeTicker used from FakeTicker class
    static FakeTicker fakeTicker = new FakeTicker();

    @Bean
    public Ticker ticker() {
      return fakeTicker;
    }
  }

  @BeforeEach
  public void setUP() {
    doReturn(Optional.empty()).when(cacheService).getCoinMarketData("", "");
  }

  @Test
  public void shouldUseCache() {
    log.info("==================Verifying Cache==================");
    // not using cache
    cacheService.getCoinMarketData(name, vs_currency);
    verify(cacheService, times(1)).getCoinMarketData(name, vs_currency);

    // should use cache now
    TestConfig.fakeTicker.advance(5, TimeUnit.MINUTES);
    cacheService.getCoinMarketData(name, vs_currency);
    verify(cacheService, times(2)).getCoinMarketData(name, vs_currency);
    log.info("loaded from cache...");
    // using cache after 10 minutes...
    TestConfig.fakeTicker.advance(10, TimeUnit.MINUTES);
    cacheService.getCoinMarketData(name, vs_currency);
    verify(cacheService, times(3)).getCoinMarketData(name, vs_currency);
    log.info("loaded from cache...");
  }
}
