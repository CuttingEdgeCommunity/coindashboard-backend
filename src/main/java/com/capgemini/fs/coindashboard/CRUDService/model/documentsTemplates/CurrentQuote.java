package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CurrentQuote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentQuote {

  private Double price;
  private List<Delta> deltas;
  private Double market_cap;
  private Double daily_volume;
  private Long last_update;
}
