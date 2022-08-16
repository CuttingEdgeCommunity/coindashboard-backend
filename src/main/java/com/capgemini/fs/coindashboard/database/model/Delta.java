package com.capgemini.fs.coindashboard.database.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Delta")
public class Delta {
  private String interval;
  private double pct;
  private double nominal;
}
