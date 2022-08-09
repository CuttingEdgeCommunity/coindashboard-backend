package com.capgemini.fs.coindashboard.dtos.marketData;

import java.util.Map;
import lombok.Data;

@Data
public class CoinMarketDataDto {

  private String name;
  private String symbol;
  private Map<String, QuoteDto> quoteMap;

  public CoinMarketDataDto(String name, String symbol, Map<String, QuoteDto> quoteMap) {
    this.name = name;
    this.symbol = symbol;
    this.quoteMap = quoteMap;
  }
}
