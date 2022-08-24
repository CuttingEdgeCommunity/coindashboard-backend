package com.capgemini.fs.coindashboard.dtos.marketData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceDto {

  private double price;
  private long timestamp;
}
