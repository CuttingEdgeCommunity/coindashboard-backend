package com.capgemini.fs.coindashboard.CRUDService.model.builder;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class CurrentQuoteBuilder {

  private Double price;
  private List<Delta> deltas;
  private Double market_cap;
  private Double daily_volume;
  private List<Price> chart;
  private Long last_update;

  public static CurrentQuoteBuilder aCurrentQuote() {
    return new CurrentQuoteBuilder();
  }

  public CurrentQuoteBuilder withPrice(Double price) {
    this.price = price;
    return this;
  }

  public CurrentQuoteBuilder withDeltas(List<Delta> deltas) {
    this.deltas = deltas;
    return this;
  }

  public CurrentQuoteBuilder withMarket_cap(Double market_cap) {
    this.market_cap = market_cap;
    return this;
  }

  public CurrentQuoteBuilder withDaily_volume(Double daily_volume) {
    this.daily_volume = daily_volume;
    return this;
  }

  public CurrentQuoteBuilder withLast_update(Long last_update) {
    this.last_update = last_update;
    return this;
  }

  public CurrentQuote build() {
    return new CurrentQuote(price, deltas, market_cap, daily_volume, last_update);
  }
}
