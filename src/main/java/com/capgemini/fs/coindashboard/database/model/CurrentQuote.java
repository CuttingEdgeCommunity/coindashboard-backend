package com.capgemini.fs.coindashboard.database.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CurrentQuote")
public class CurrentQuote {

  private long price;
  private List<Delta> deltas;
  private long market_cap;
  private long daily_volume;
  private List<Price> chart;
}
