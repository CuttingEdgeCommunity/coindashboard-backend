package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Price")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
  private Double price;
  private Long timestamp;
}
