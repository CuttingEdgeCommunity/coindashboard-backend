package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import lombok.Data;

@Data
public class PriceDto {

  private float price;
  private long timestamp;

  public PriceDto(float price, long timestamp) {
    this.price = price;
    this.timestamp = timestamp;
  }
}
