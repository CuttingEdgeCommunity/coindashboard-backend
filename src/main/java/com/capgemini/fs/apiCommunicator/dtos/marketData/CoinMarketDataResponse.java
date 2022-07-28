package com.capgemini.fs.apiCommunicator.dtos.marketData;

import com.capgemini.fs.apiCommunicator.dtos.coinDetails.CoinDetailsDTO;
import com.capgemini.fs.apiCommunicator.dtos.common.Response;
import com.capgemini.fs.apiCommunicator.dtos.common.ResponseStatus;
import java.util.ArrayList;

public class CoinMarketDataResponse extends Response {

  private final ArrayList<CoinMarketDataDTO> coinMarketDataDTOS;

  public CoinMarketDataResponse(ResponseStatus status, String errorMessage,
                                ArrayList<CoinMarketDataDTO> coinMarketDataDTOS) {
    super(status, errorMessage);
    this.coinMarketDataDTOS = coinMarketDataDTOS;
  }

  public ArrayList<CoinMarketDataDTO> getCoinDetailsDTOs() {
    return coinMarketDataDTOS;
  }
}
