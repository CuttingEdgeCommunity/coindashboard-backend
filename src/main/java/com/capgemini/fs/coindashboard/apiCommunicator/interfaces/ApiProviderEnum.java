package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApiProviderEnum {
  COIN_MARKET_CAP(1),
  COIN_GECKO(2);
  public final Integer order;
}
