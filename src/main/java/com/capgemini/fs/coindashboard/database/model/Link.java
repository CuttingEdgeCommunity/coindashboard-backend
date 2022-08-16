package com.capgemini.fs.coindashboard.database.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Link")
public class Link {
  private String title;
  private String url;
}
