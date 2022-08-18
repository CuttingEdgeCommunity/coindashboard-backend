package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Coin")
public class Coin {

  @Id private String id;

  @Indexed(name = "coinName")
  private String name;

  private String symbol;
  private String image_url;
  private Long genesis_date;
  private Boolean is_token;
  private String contract_address;
  private List<Link> links;
  private String description;
  private List<Quote> quotes;

  public Coin(
      String id,
      String name,
      String symbol,
      String image_url,
      Long genesis_date,
      Boolean is_token,
      String contract_address,
      List<Link> links,
      String description,
      List<Quote> quotes) {
    this.id = id;
    this.name = name;
    this.symbol = symbol;
    this.image_url = image_url;
    this.genesis_date = genesis_date;
    this.is_token = is_token;
    this.contract_address = contract_address;
    this.links = links;
    this.description = description;
    this.quotes = quotes;
  }
}
