package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Coin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin {

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @Id
  private String id;

  @Indexed(name = "coinName")
  private String name;

  @Indexed(name = "symbol")
  private String symbol;

  @Indexed(name = "rank")
  private Integer marketCapRank;

  private String image_url;
  private Long genesis_date;
  private Boolean is_token;
  private String contract_address;
  private List<Link> links;
  private String description;
  private Map<String, Quote> quotes;

  public Coin(Coin info, Coin marketData){
      this.contract_address = info.contract_address;
      this.image_url = info.image_url;
      this.genesis_date = info.genesis_date;
      this.is_token = info.is_token;
      this.description = info.description;
      this.links = info.links;
      this.symbol = info.symbol;

      this.quotes = marketData.quotes;
      this.marketCapRank = marketData.marketCapRank;
      this.name = marketData.name;
  }
}
