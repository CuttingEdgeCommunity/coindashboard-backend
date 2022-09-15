package com.capgemini.fs.coindashboard.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AsyncConfig.class})
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
class AsyncConfigTest {
  @Autowired AsyncConfig appConfig;

  @Value("${thread-pool-config.core-pool-size}")
  private Integer core_pool_size;

  @Value("${thread-pool-config.queue-capacity}")
  private Integer queue_capacity;

  @Test
  void getAsyncExecutor() throws NoSuchFieldException, IllegalAccessException {

    var exec = this.appConfig.getAsyncExecutor();
    Field cps = ThreadPoolTaskExecutor.class.getDeclaredField("corePoolSize");
    cps.setAccessible(true);
    Field mps = ThreadPoolTaskExecutor.class.getDeclaredField("maxPoolSize");
    mps.setAccessible(true);
    Field qc = ThreadPoolTaskExecutor.class.getDeclaredField("queueCapacity");
    qc.setAccessible(true);
    Field e = ThreadPoolTaskExecutor.class.getDeclaredField("threadPoolExecutor");
    e.setAccessible(true);
    assertEquals(core_pool_size, cps.get(exec));
    assertEquals(Integer.MAX_VALUE, mps.get(exec));
    assertEquals(queue_capacity, qc.get(exec));
    assertNotNull(e.get(exec));
  }
}
