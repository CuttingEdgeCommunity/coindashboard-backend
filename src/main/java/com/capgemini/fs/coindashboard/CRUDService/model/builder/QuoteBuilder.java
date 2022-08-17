package com.capgemini.fs.coindashboard.CRUDService.model.builder;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;

public final class QuoteBuilder {

  private String vs_currency;
  private CurrentQuote currentQuote;

  private QuoteBuilder() {}

  public static QuoteBuilder aQuote() {
    return new QuoteBuilder();
  }

  public QuoteBuilder withVs_currency(String vs_currency) {
    this.vs_currency = vs_currency;
    return this;
  }

  public QuoteBuilder withCurrentQuote(CurrentQuote currentQuote) {
    this.currentQuote = currentQuote;
    return this;
  }

  public Quote build() {
    return new Quote(vs_currency, currentQuote);
  }
}
