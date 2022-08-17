package com.capgemini.fs.coindashboard.database.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CurrentQuote")
public class CurrentQuote {

  private long price;
  private List<Delta> deltas;
  private long market_cap;
  private long daily_volume;
  private List<Price> chart;

  public CurrentQuote(
      long price, List<Delta> deltas, long market_cap, long daily_volume, List<Price> chart) {
    this.price = price;
    this.deltas = deltas;
    this.market_cap = market_cap;
    this.daily_volume = daily_volume;
    this.chart = chart;
  }
}
