package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import lombok.Data;
import java.util.List;

@Data
public class CoinMarketDataDto {
  private String name;
  private String symbol;
  private List<QuoteDto> quotes;
  private Long timestamp;
}
