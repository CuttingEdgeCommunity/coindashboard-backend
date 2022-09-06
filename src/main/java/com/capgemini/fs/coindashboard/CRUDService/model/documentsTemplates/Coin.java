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
}
