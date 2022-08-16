package com.capgemini.fs.coindashboard.database.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Coin")
public class Coin {
  @Id private String id;

  @Indexed(name = "coinName")
  private String name;

  private String image_url;
  private long genesis_date;
  private boolean is_token;
  private String contract_address;
  private List<Link> links;
  private String description;
  private List<Quote> quotes;
}
