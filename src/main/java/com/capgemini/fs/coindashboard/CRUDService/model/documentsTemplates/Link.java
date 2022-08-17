package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Link")
public class Link {
  private String title;
  private String url;

  public Link(String title, String url) {
    this.title = title;
    this.url = url;
  }
}
