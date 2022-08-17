package com.capgemini.fs.coindashboard.database.model;

public final class LinkBuilder {

  private String title;
  private String url;

  private LinkBuilder() {}

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
