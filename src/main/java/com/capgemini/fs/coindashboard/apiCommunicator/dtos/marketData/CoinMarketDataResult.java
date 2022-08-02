package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CoinMarketDataResult extends Result {

  private ArrayList<CoinMarketDataDto> coinMarketDataDTOS;

  public CoinMarketDataResult(ResultStatus status, String errorMessage,
      ArrayList<CoinMarketDataDto> coinMarketDataDTOS) {
    super(status, errorMessage);
    this.coinMarketDataDTOS = coinMarketDataDTOS;
  }
}
