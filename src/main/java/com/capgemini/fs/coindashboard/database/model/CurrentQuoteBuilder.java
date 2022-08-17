package com.capgemini.fs.coindashboard.database.model;

import java.util.List;

public final class CurrentQuoteBuilder {

  private double price;
  private List<Delta> deltas;
  private double market_cap;
  private double daily_volume;
  private List<Price> chart;

  private CurrentQuoteBuilder() {}

  public static CurrentQuoteBuilder aCurrentQuote() {
    return new CurrentQuoteBuilder();
  }

  public CurrentQuoteBuilder withPrice(double price) {
    this.price = price;
    return this;
  }

  public CurrentQuoteBuilder withDeltas(List<Delta> deltas) {
    this.deltas = deltas;
    return this;
  }

  public CurrentQuoteBuilder withMarket_cap(double market_cap) {
    this.market_cap = market_cap;
    return this;
  }

  public CurrentQuoteBuilder withDaily_volume(double daily_volume) {
    this.daily_volume = daily_volume;
    return this;
  }

  public CurrentQuoteBuilder withChart(List<Price> chart) {
    this.chart = chart;
    return this;
  }

  public CurrentQuote build() {
    return new CurrentQuote(price, deltas, market_cap, daily_volume, chart);
  }
}
