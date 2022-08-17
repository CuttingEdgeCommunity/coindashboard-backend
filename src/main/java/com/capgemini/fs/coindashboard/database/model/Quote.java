package com.capgemini.fs.coindashboard.database.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Quote")
public class Quote {
  private String vs_currency;
  private CurrentQuote currentQuote;

  public Quote(String vs_currency, CurrentQuote currentQuote) {
    this.vs_currency = vs_currency;
    this.currentQuote = currentQuote;
  }
}
