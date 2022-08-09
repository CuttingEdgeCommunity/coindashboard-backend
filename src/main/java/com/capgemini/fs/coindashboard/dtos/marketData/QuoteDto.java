package com.capgemini.fs.coindashboard.dtos.marketData;

import java.util.List;
import lombok.Data;

@Data
public class QuoteDto {

  private List<PriceDto> prices;
  private List<DeltaDto> deltas;
  private long lastUpdateTimestampMillis;

  public QuoteDto(List<PriceDto> prices, List<DeltaDto> deltas, long lastUpdateTimestampMillis) {
    this.prices = prices;
    this.deltas = deltas;
    this.lastUpdateTimestampMillis = lastUpdateTimestampMillis;
  }
}
