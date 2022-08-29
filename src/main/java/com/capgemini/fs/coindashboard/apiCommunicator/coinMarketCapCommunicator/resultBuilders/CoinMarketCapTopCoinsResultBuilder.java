package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapTopCoinsResultBuilder extends CoinMarketCapMarketDataBuilderBaseClass {

  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.TOP_COINS;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }
}
