package com.capgemini.fs.coindashboard.API_documentation;

//link to aPI controller
//http://localhost:8080/swagger-ui/index.html#/
public class Greeting {

  private final long id;
  private final String content;

  public Greeting(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}