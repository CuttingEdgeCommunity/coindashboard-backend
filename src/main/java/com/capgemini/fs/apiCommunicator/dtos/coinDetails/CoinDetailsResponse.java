package com.capgemini.fs.apiCommunicator.dtos.coinDetails;

import com.capgemini.fs.apiCommunicator.dtos.common.Response;
import com.capgemini.fs.apiCommunicator.dtos.common.ResponseStatus;
import java.util.ArrayList;

public class CoinDetailsResponse extends Response {

  private final ArrayList<CoinDetailsDTO> coinDetailsDTOs;

  public CoinDetailsResponse(ResponseStatus status, String errorMessage,
      ArrayList<CoinDetailsDTO> coinDetailsDTOs) {
    super(status, errorMessage);
    this.coinDetailsDTOs = coinDetailsDTOs;
  }

  public ArrayList<CoinDetailsDTO> getCoinDetailsDTOs() {
    return coinDetailsDTOs;
  }
}
