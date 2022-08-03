package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import java.util.List;
import lombok.Data;

@Data
public class CoinMarketDataDto {

  private String name;
  private String symbol;
  private List<QuoteDto> quotes;
  private Long timestampMillis;

  public CoinMarketDataDto(String name, String symbol, List<QuoteDto> quotes,
      Long timestampMillis) {
    this.name = name;
    this.symbol = symbol;
    this.quotes = quotes;
    this.timestampMillis = timestampMillis;
  }
}
