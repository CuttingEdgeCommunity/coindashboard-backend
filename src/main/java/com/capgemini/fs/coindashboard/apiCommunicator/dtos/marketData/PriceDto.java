package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import lombok.Data;

@Data
public class PriceDto {

  private double price;
  private long timestamp;

  public PriceDto(double price, long timestamp) {
    this.price = price;
    this.timestamp = timestamp;
  }
}
