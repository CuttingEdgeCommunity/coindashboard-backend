package com.capgemini.fs.coindashboard.cacheService;

import com.github.benmanes.caffeine.cache.Ticker;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FakeTicker implements Ticker {
  private final AtomicLong nanos = new AtomicLong();

  /** Advances the ticker value by {@code time} in {@code timeUnit}. */
  public FakeTicker advance(long time, TimeUnit timeUnit) {
    nanos.addAndGet(timeUnit.toNanos(time));
    return this;
  }

  @Override
  public long read() {
    long value = nanos.getAndAdd(0);
    System.out.println("is called " + value);
    return value;
  }
}
