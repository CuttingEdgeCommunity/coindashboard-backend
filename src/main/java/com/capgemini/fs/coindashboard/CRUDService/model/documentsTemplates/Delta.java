package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Delta")
@Data
@NoArgsConstructor
public class Delta {
  private String interval;
  private Double pct;
  private Double nominal;

  public Delta(String interval, Double pct, Double nominal) {
    this.interval = interval;
    this.pct = pct;
    this.nominal = nominal;
  }
}
