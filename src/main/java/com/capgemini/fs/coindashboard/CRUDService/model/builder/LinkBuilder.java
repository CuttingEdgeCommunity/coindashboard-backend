package com.capgemini.fs.coindashboard.CRUDService.model.builder;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class LinkBuilder {

  private String title;
  private String url;

  public static LinkBuilder aLink() {
    return new LinkBuilder();
  }

  public LinkBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public LinkBuilder withUrl(String url) {
    this.url = url;
    return this;
  }

  public Link build() {
    return new Link(title, url);
  }
}
