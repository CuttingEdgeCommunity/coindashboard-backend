package com.capgemini.fs.coindashboard.cacheService.cacheConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CacheEnv.class)
@ConfigurationProperties(prefix = "cache.config")
@Getter
@Setter
public class CacheEnv {
  private Integer max_size_get_coin_market_data;
  private Integer expire_milliseconds_get_coin_market_data;
  private Integer max_size_get_coin_info;
  private Integer expire_milliseconds_get_coin_info;
  private Integer max_size_get_coin_details;
  private Integer expire_milliseconds_get_coin_details;
  private Integer max_size_get_chart;
  private Integer expire_milliseconds_get_chart;
  private Integer max_size_get_coin_by_regex;
  private Integer expire_milliseconds_get_coin_by_regex;
}
