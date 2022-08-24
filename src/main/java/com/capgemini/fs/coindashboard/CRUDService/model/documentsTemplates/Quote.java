package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Quote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
  private String vs_currency;
  private CurrentQuote currentQuote;
  private List<Price> chart;
}
