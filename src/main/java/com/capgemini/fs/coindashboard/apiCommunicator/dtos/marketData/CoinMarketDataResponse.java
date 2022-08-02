package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper=true)
public class CoinMarketDataResponse extends Response {
  private ArrayList<CoinMarketDataDto> coinMarketDataDTOS;
}
