package com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Coin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin {

  @Id private String id;

  @Indexed(name = "coinName")
  private String name;

  @Indexed private Integer market_cap_rank;

  private String symbol;
  private String image_url;
  private Long genesis_date;
  private Boolean is_token;
  private String contract_address;
  private List<Link> links;
  private String description;
  private Map<String, Quote> quotes;
}
