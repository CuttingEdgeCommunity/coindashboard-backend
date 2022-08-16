package com.capgemini.fs.coindashboard.database.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Quote")
public class Quote {
  private String vs_currency;
  private CurrentQuote currentQuote;
}
