package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CurrentQuote")
@Data
@NoArgsConstructor
public class CurrentQuote {

  private Double price;
  private List<Delta> deltas;
  private Double market_cap;
  private Double daily_volume;

  public CurrentQuote(Double price, List<Delta> deltas, Double market_cap, Double daily_volume) {
    this.price = price;
    this.deltas = deltas;
    this.market_cap = market_cap;
    this.daily_volume = daily_volume;
  }
}
