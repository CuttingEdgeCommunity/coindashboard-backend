package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import lombok.Data;

@Data
public class DeltaDto {

  private IntervalEnum interval;
  private float percentChange;
  private float nominalChange;
}
