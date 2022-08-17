package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Quote")
public class Quote {
  private String vs_currency;
  private CurrentQuote currentQuote;
  private List<Price> chart;

  public Quote(String vs_currency, CurrentQuote currentQuote, List<Price> chart) {
    this.vs_currency = vs_currency;
    this.currentQuote = currentQuote;
    this.chart = chart;
  }
}
