package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper=true)
public class CoinMarketDataResult extends Result {
  private ArrayList<CoinMarketDataDto> coinMarketDataDTOS;
}
