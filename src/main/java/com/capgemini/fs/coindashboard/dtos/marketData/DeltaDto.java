package com.capgemini.fs.coindashboard.dtos.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeltaDto {

  private IntervalEnum interval;
  private double percentChange;
  private double nominalChange;
}
