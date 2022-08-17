package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.builders;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.resultBuilder.CoinMarketDataResultBuilder;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum;
import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoinMarketCapCurrentListingBuilder implements CoinMarketDataResultBuilder {

  private CoinMarketDataResult result;
  private String errorMessage;
  private boolean isPartialSuccess; // figure this out
  private final ApiProviderEnum apiProviderEnum = ApiProviderEnum.COIN_MARKET_CAP;

  public CoinMarketCapCurrentListingBuilder() {
    this.initialize();
  }

  @Override
  public void reset() {
    this.initialize();
  }

  private void initialize() {
    this.result = new CoinMarketDataResult();
    this.errorMessage = null;
    this.isPartialSuccess = false;
  }

  @Override
  public void setResultProvider() {
    this.result.setProvider(this.apiProviderEnum);
  }

  @Override
  public void setResultStatus() {
    this.result.setStatus(
        (this.errorMessage != null && !this.errorMessage.equals(""))
            ? ResultStatus.FAILURE
            : ResultStatus.SUCCESS);
  }

  @Override
  public void setErrorMessage() {
    this.result.setErrorMessage(this.errorMessage);
  }

  public String parseStatus(JsonNode status) {
    if (status.get("error_code").asInt() != 0) {
      return status.get("error_message").asText();
    }
    return null;
  }

  @Override
  public void setCoinMarketDataDTOS(JsonNode data) {
    if (parseStatus(data.get("status")) != null) {
      this.errorMessage = parseStatus(data.get("status"));
    } else {
      this.result.setCoinMarketDataDTOS(this.buildCoinMarketDataDtoList(data.get("data")));
    }
  }

  @Override
  public List<CoinMarketDataDto> buildCoinMarketDataDtoList(JsonNode data) {
    var result = new ArrayList<CoinMarketDataDto>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, JsonNode> responseBodyConverted =
        mapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, JsonNode> coin : responseBodyConverted.entrySet()) {
      result.add(buildSingleCoinMarketDataDto(coin.getKey(), coin.getValue()));
    }
    return result;
  }

  @Override
  public CoinMarketDataDto buildSingleCoinMarketDataDto(String coinName, JsonNode data) {
    var result = new CoinMarketDataDto();
    result.setName("");
    result.setSymbol("");
    result.setQuoteMap(this.buildQuoteDtoMap(data));
    return result;
  }

  @Override
  public Map<String, QuoteDto> buildQuoteDtoMap(JsonNode data) {
    var result = new LinkedHashMap<String, QuoteDto>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, QuoteDto> quoteDtos = new LinkedHashMap<>();
    Map<String, ObjectNode> quotesConverted =
        mapper.convertValue(data.get("quote"), new TypeReference<>() {});
    for (Map.Entry<String, ObjectNode> quote : quotesConverted.entrySet()) {
      result.put(quote.getKey(), this.buildSingleQuote(quote.getValue()));
    }
    return result;
  }

  @Override
  public QuoteDto buildSingleQuote(JsonNode data) {
    var result = new QuoteDto();
    result.setMarketCap(0);
    result.setCurrentPrice(0);
    result.setLastUpdateTimestampMillis(0);
    result.setVolumeOneDay(0);
    result.setDeltas(this.buildDeltaDtoList(data));
    result.setPriceHistory(
        null); // this is okay here, since we won't get any historical data from cmc in this call
    return result;
  }

  @Override
  public List<PriceDto> buildPriceDtoList(JsonNode data) {
    return null; // this can stay null here
  }

  @Override
  public List<DeltaDto> buildDeltaDtoList(JsonNode data) {
    var result = new ArrayList<DeltaDto>();
    for (IntervalEnum intervalEnum : IntervalEnum.values()) {
      result.add(this.buildSingleDeltaDto(data, intervalEnum));
    }
    return result;
  }

  @Override
  public PriceDto buildSinglePriceDto(JsonNode data) {
    return null; // this can stay null here
  }

  @Override
  public DeltaDto buildSingleDeltaDto(JsonNode data, IntervalEnum delta) {
    return null;
  }

  @Override
  public CoinMarketDataResult getResult() {
    return this.result;
  }
}
