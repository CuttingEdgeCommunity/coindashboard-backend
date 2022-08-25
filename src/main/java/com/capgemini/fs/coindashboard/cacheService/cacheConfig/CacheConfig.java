package com.capgemini.fs.coindashboard.cacheService.cacheConfig;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
  // TODO: we need to make it synchronized with update and custom for every method
  // in CacheService

  @Bean
  public Caffeine caffeineConfig() {
    return Caffeine.newBuilder().expireAfterWrite(1000, TimeUnit.MILLISECONDS);
  }

  @Bean
  public CacheManager cacheManager(Caffeine caffeine) {
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    caffeineCacheManager.setCaffeine(caffeine);
    return caffeineCacheManager;
  }
}
