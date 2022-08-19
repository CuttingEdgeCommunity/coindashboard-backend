package com.capgemini.fs.coindashboard.CRUDService.model.builder;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;

public final class DeltaBuilder {

  private String interval;
  private Double pct;
  private Double nominal;

  private DeltaBuilder() {}

  public static DeltaBuilder aDelta() {
    return new DeltaBuilder();
  }

  public DeltaBuilder withInterval(String interval) {
    this.interval = interval;
    return this;
  }

  public DeltaBuilder withPct(Double pct) {
    this.pct = pct;
    return this;
  }

  public DeltaBuilder withNominal(Double nominal) {
    this.nominal = nominal;
    return this;
  }

  public Delta build() {
    return new Delta(interval, pct, nominal);
  }
}
