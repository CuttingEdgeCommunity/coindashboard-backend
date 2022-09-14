package com.capgemini.fs.coindashboard;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableAsync
@EnableConfigurationProperties
public class AppConfig implements AsyncConfigurer {
  @Value("${thread-pool-config.core-pool-size}")
  private Integer core_pool_size;

  @Value("${thread-pool-config.queue-capacity}")
  private Integer queue_capacity;

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(this.core_pool_size);
    executor.setQueueCapacity(this.queue_capacity);
    executor.setThreadNamePrefix("MyExecutor-");
    executor.initialize();
    return executor;
  }
}
