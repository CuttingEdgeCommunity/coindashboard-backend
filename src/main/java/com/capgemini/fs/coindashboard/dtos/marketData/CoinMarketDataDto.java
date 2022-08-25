package com.capgemini.fs.coindashboard.dtos.marketData;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoinMarketDataDto {

  private String name;
  private String symbol;
  private Map<String, QuoteDto> quoteMap;

  public CoinMarketDataDto(String name, Map<String, QuoteDto> quoteMap) {
    this.name = name;
    this.quoteMap = quoteMap;
  }
}
