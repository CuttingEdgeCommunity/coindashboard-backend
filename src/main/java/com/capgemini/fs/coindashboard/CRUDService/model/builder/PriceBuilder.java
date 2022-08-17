package com.capgemini.fs.coindashboard.CRUDService.model.builder;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;

public final class PriceBuilder {

  private double price;
  private long timestamp;

  private PriceBuilder() {}

  public static PriceBuilder aPrice() {
    return new PriceBuilder();
  }

  public PriceBuilder withPrice(double price) {
    this.price = price;
    return this;
  }

  public PriceBuilder withTimestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public Price build() {
    return new Price(price, timestamp);
  }
}
