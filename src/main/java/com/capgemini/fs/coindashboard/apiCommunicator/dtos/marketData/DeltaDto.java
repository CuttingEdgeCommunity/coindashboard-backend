package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import lombok.Data;

@Data
public class DeltaDto {

  private IntervalEnum interval;
  private double percentChange;
  private double nominalChange;

  public DeltaDto(IntervalEnum interval, double percentChange, double nominalChange) {
    this.interval = interval;
    this.percentChange = percentChange;
    this.nominalChange = nominalChange;
  }
}
