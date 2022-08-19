package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Price")
@Data
@NoArgsConstructor
public class Price {
  private Double price;
  private Long timestamp;

  public Price(Double price, Long timestamp) {
    this.price = price;
    this.timestamp = timestamp;
  }
}
