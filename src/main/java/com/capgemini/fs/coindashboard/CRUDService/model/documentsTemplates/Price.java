package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Price")
public class Price {
  private double price;
  private long timestamp;

  public Price(double price, long timestamp) {
    this.price = price;
    this.timestamp = timestamp;
  }
}
