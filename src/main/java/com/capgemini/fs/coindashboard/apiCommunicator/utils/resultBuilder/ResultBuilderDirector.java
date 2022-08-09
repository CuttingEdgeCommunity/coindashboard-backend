package com.capgemini.fs.coindashboard.apiCommunicator.utils.resultBuilder;

import com.fasterxml.jackson.databind.JsonNode;

public class ResultBuilderDirector {

  void constructCoinMarketDataResult(CoinMarketDataResultBuilder builder, JsonNode data) {
    builder.reset();
    builder.setResultProvider();
    builder.setCoinMarketDataDTOS(data);
    builder.setErrorMessage();
    builder.setResultStatus();
  }
}
