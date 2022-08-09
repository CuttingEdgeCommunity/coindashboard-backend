package com.capgemini.fs.coindashboard.apiCommunicator.utils.resultBuilder;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.IntervalEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.QuoteDto;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;

public interface CoinMarketDataResultBuilder {

  void reset();

  void setResultProvider();

  void setResultStatus();

  void setErrorMessage();

  void setCoinMarketDataDTOS(JsonNode data);

  CoinMarketDataDto buildSingleCoinMarketDataDto(JsonNode data);

  Map<String, QuoteDto> buildQuoteDtoMap(JsonNode data);

  QuoteDto buildSingleQuote(JsonNode data);

  List<PriceDto> buildPriceDtoList(JsonNode data);

  List<DeltaDto> buildDeltaDtoList(JsonNode data);

  PriceDto buildSinglePriceDto(JsonNode data);

  DeltaDto buildSingleDeltaDto(JsonNode data, IntervalEnum delta);

  CoinMarketDataResult getResult();
}
