package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Link")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
  private String title;
  private String url;
}
