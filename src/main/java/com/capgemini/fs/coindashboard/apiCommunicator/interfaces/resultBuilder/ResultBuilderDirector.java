package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import org.springframework.stereotype.Component;

@Component
public class ResultBuilderDirector {
  public void constructCoinMarketDataResult(
      IResultBuilder builder, Response response, Object... requestArgs) {
    builder.reset();
    builder.setData(response, requestArgs);
    builder.setResultProvider();
    builder.setResultStatus();
    if (builder.getResult().getStatus() == ResultStatus.SUCCESS) {
      builder.setCoins();
      builder.setResultStatus();
    }
    builder.setErrorMessage();
  }
}
