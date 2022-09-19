package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApiProviderEnum {
  COIN_GECKO(1),
  COIN_MARKET_CAP(2);
  public final Integer order;
}
