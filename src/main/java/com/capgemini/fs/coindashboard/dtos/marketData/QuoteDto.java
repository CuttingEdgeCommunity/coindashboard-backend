package com.capgemini.fs.coindashboard.dtos.marketData;

import java.util.List;
import lombok.Data;

@Data
public class QuoteDto {

  private double currentPrice;
  private double marketCap;
  private double volumeOneDay;
  private List<PriceDto> priceHistory;
  private List<DeltaDto> deltas;
  private long lastUpdateTimestampMillis;

  public QuoteDto(
      double currentPrice,
      double marketCap,
      double volumeOneDay,
      List<PriceDto> priceHistory,
      List<DeltaDto> deltas,
      long lastUpdateTimestampMillis) {
    this.currentPrice = currentPrice;
    this.marketCap = marketCap;
    this.volumeOneDay = volumeOneDay;
    this.priceHistory = priceHistory;
    this.deltas = deltas;
    this.lastUpdateTimestampMillis = lastUpdateTimestampMillis;
  }
}
