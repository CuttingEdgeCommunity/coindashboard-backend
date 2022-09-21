package com.capgemini.fs.coindashboard.cacheService.cacheConfig;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
  @Autowired private CacheEnv cacheEnv;

  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    // caffeineCacheManager.setCaffeine(caffeine);
    caffeineCacheManager.registerCustomCache(
        "getCoinMarketData",
        Caffeine.newBuilder()
            .maximumSize(cacheEnv.getMax_size_get_coin_market_data())
            .expireAfterWrite(
                cacheEnv.getExpire_milliseconds_get_coin_market_data(), TimeUnit.MILLISECONDS)
            .build());
    caffeineCacheManager.registerCustomCache(
        "getCoinInfo",
        Caffeine.newBuilder()
            .maximumSize(cacheEnv.getMax_size_get_coin_info())
            .expireAfterWrite(
                cacheEnv.getExpire_milliseconds_get_coin_info(), TimeUnit.MILLISECONDS)
            .build());
    caffeineCacheManager.registerCustomCache(
        "getCoinDetails",
        Caffeine.newBuilder()
            .maximumSize(cacheEnv.getMax_size_get_coin_details())
            .expireAfterWrite(
                cacheEnv.getExpire_milliseconds_get_coin_details(), TimeUnit.MILLISECONDS)
            .build());
    caffeineCacheManager.registerCustomCache(
        "getChart",
        Caffeine.newBuilder()
            .maximumSize(cacheEnv.getMax_size_get_chart())
            .expireAfterWrite(cacheEnv.getExpire_milliseconds_get_chart(), TimeUnit.MILLISECONDS)
            .build());
    caffeineCacheManager.registerCustomCache(
        "getCoinByRegex",
        Caffeine.newBuilder()
            .maximumSize(cacheEnv.getMax_size_get_coin_by_regex())
            .expireAfterWrite(
                cacheEnv.getExpire_milliseconds_get_coin_by_regex(), TimeUnit.MILLISECONDS)
            .build());
    return caffeineCacheManager;
  }
}
