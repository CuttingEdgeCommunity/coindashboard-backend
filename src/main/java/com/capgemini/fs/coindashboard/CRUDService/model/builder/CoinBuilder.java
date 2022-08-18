package com.capgemini.fs.coindashboard.CRUDService.model.builder;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Link;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import java.util.List;
import java.util.Map;

public final class CoinBuilder {

  private String id;
  private String name;
  private String symbol;
  private String image_url;
  private long genesis_date;
  private boolean is_token;
  private String contract_address;
  private List<Link> links;
  private String description;
  private Map<String, Quote> quotes;

  private CoinBuilder() {}

  public static CoinBuilder aCoin() {
    return new CoinBuilder();
  }

  public CoinBuilder withId(String id) {
    this.id = id;
    return this;
  }

  public CoinBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public CoinBuilder withSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public CoinBuilder withImage_url(String image_url) {
    this.image_url = image_url;
    return this;
  }

  public CoinBuilder withGenesis_date(long genesis_date) {
    this.genesis_date = genesis_date;
    return this;
  }

  public CoinBuilder withIs_token(boolean is_token) {
    this.is_token = is_token;
    return this;
  }

  public CoinBuilder withContract_address(String contract_address) {
    this.contract_address = contract_address;
    return this;
  }

  public CoinBuilder withLinks(List<Link> links) {
    this.links = links;
    return this;
  }

  public CoinBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public CoinBuilder withQuotes(Map<String, Quote> quotes) {
    this.quotes = quotes;
    return this;
  }

  public Coin build() {
    return new Coin(
        id,
        name,
        symbol,
        image_url,
        genesis_date,
        is_token,
        contract_address,
        links,
        description,
        quotes);
  }
}
