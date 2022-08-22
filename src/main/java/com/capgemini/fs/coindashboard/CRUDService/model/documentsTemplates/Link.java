package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Link")
@Data
@NoArgsConstructor
public class Link {
  private String title;
  private String url;

  public Link(String title, String url) {
    this.title = title;
    this.url = url;
  }
}
