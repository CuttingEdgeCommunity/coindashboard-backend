package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CurrentQuote")
public class CurrentQuote {

  private double price;
  private List<Delta> deltas;
  private double market_cap;
  private double daily_volume;

  public CurrentQuote(double price, List<Delta> deltas, double market_cap, double daily_volume) {
    this.price = price;
    this.deltas = deltas;
    this.market_cap = market_cap;
    this.daily_volume = daily_volume;
  }
}
