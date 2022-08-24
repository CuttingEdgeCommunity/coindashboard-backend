package com.capgemini.fs.coindashboard.dtos.marketData;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {

  private double currentPrice;
  private double marketCap;
  private double volumeOneDay;
  private List<PriceDto> priceHistory;
  private List<DeltaDto> deltas;
  private long lastUpdateTimestampMillis;

  public QuoteDto(List<PriceDto> priceHistory) {
    this();
    this.priceHistory = priceHistory;
  }
}
