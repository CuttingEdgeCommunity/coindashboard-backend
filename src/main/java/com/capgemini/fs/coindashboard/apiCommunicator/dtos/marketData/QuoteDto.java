package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import java.util.List;
import lombok.Data;

@Data
public class QuoteDto {

  private String vsCurrency;
  private float price;
  private List<DeltaDto> deltas;
  private long lastUpdateTimestampMillis;

  public QuoteDto(String vsCurrency, float price, List<DeltaDto> deltas,
      long lastUpdateTimestampMillis) {
    this.vsCurrency = vsCurrency;
    this.price = price;
    this.deltas = deltas;
    this.lastUpdateTimestampMillis = lastUpdateTimestampMillis;
  }
}

