package com.capgemini.fs.apiCommunicator.dtos.marketData;

import java.util.List;

public class Quote {
  private String vsCurrency;
  private float price;
  private List<Delta> deltas;
  private long lastUpdateTimestamp;
}

