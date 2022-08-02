package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.Result;
import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CoinMarketDataResult extends Result {

  private ArrayList<CoinMarketDataDto> coinMarketDataDTOS;
}
