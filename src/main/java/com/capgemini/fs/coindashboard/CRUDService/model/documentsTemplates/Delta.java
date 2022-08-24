package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Delta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delta {
  private String interval;
  private Double pct;
  private Double nominal;
}
