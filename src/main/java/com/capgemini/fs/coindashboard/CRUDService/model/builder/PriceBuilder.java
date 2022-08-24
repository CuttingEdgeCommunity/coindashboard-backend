package com.capgemini.fs.coindashboard.CRUDService.model.builder;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class PriceBuilder {

  private Double price;
  private Long timestamp;

  public static PriceBuilder aPrice() {
    return new PriceBuilder();
  }

  public PriceBuilder withPrice(Double price) {
    this.price = price;
    return this;
  }

  public PriceBuilder withTimestamp(Long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public Price build() {
    return new Price(price, timestamp);
  }
}
