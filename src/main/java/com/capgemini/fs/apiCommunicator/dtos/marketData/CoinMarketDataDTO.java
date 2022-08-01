package com.capgemini.fs.apiCommunicator.dtos.marketData;

import java.util.List;

public class CoinMarketDataDTO {
  private String name;
  private String symbol;
  private List<Quote> quotes;
  private Long timestamp;
}
