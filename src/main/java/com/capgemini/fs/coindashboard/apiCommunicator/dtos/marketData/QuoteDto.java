package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import lombok.Data;
import java.util.List;

@Data
public class QuoteDto {
  private String vsCurrency;
  private float price;
  private List<DeltaDto> deltas;
  private long lastUpdateTimestamp;
}

