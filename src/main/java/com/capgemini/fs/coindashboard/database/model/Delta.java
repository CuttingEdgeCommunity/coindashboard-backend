package com.capgemini.fs.coindashboard.database.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Delta")
public class Delta {
  private String interval;
  private double pct;
  private double nominal;

  public Delta(String interval, double pct, double nominal) {
    this.interval = interval;
    this.pct = pct;
    this.nominal = nominal;
  }
}
