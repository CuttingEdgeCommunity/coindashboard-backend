package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.IntervalEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.QuoteDto;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.ParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class CoinMarketCapResponseParser {

  private static final Logger log = LogManager.getLogger(CoinMarketCapResponseParser.class);

  private String parseStatus(JsonNode status) {
    if (status.get("error_code").asInt() != 0) {
      return status.get("error_message").asText();
    }
    return null;
  }

  public CoinMarketDataResult parseGetCoinsQuoteResult(Response apiResponse) {
    CoinMarketDataResult result = new CoinMarketDataResult();
    try {
      String error = this.parseStatus(apiResponse.getResponseBody().get("status"));
      if (error != null) {
        result.setStatus(ResultStatus.FAILURE);
        result.setErrorMessage(error);
        return result;
      }

      result.setStatus(ResultStatus.SUCCESS);
      result.setCoinMarketDataDTOS(
          this.parseMarketDataLatestQuote(apiResponse.getResponseBody().get("data")));
      return result;
    } catch (Exception e) {
      log.error(e.getMessage());
      result.setStatus(ResultStatus.FAILURE);
      result.setErrorMessage(e.getMessage());
      return result;
    }
  }

  public ArrayList<CoinMarketDataDto> parseMarketDataLatestQuote(JsonNode data)
      throws ParseException {
    ArrayList<CoinMarketDataDto> result = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, JsonNode> responseBodyConverted = mapper.convertValue(data,
        new TypeReference<>() {
        });
    for (Map.Entry<String, JsonNode> coin :
        responseBodyConverted.entrySet()) {
      if (coin.getValue().getNodeType()
          == JsonNodeType.ARRAY) // For some reason cmc sometimes returns an array of coins for a symbol
      {
        coin = new SimpleEntry<>(coin.getKey(), coin.getValue().get(0));
      }
      String name = coin.getKey();
      String symbol = coin.getValue().get("symbol").asText();
      long lastUpdated = TimeFormatter.convertStringToTimestamp(
          coin.getValue().get("last_updated").asText(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").getTime();

      List<QuoteDto> quoteDtoList = new ArrayList<>();
      Map<String, ObjectNode> quotesConverted = mapper.convertValue(coin.getValue().get("quote"),
          new TypeReference<>() {
          });

      for (Map.Entry<String, ObjectNode> quote : quotesConverted.entrySet()) {
        var currentPrice = (float) quote.getValue().get("price").asDouble();
        var timestamp = TimeFormatter.convertStringToTimestamp(
                quote.getValue().get("last_updated").asText(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .getTime();
        var pctChange1h = (float) quote.getValue().get("percent_change_1h").asDouble();
        var pctChange24h = (float) quote.getValue().get("percent_change_24h").asDouble();
        var pctChange7d = (float) quote.getValue().get("percent_change_7d").asDouble();
        var pctChange30d = (float) quote.getValue().get("percent_change_30d").asDouble();
        var nominalChange1h = currentPrice * pctChange1h;
        var nominalChange24h = currentPrice * pctChange24h;
        var nominalChange7d = currentPrice * pctChange7d;
        var nominalChange30d = currentPrice * pctChange30d;
        List<DeltaDto> deltas = new ArrayList<>() {{
          add(new DeltaDto(IntervalEnum.ONE_HOUR, pctChange1h, nominalChange1h));
          add(new DeltaDto(IntervalEnum.ONE_DAY, pctChange24h, nominalChange24h));
          add(new DeltaDto(IntervalEnum.SEVEN_DAY, pctChange7d, nominalChange7d));
          add(new DeltaDto(IntervalEnum.THIRTY_DAY, pctChange30d, nominalChange30d));
        }};

        quoteDtoList.add(new QuoteDto(quote.getKey(), currentPrice, deltas, timestamp));
      }
      result.add(new CoinMarketDataDto(
          name, symbol, quoteDtoList, lastUpdated));
    }
    return result;
  }


  public CoinMarketDataResult parseGetCoinsHistoricalQuoteResult(Response apiResponse) {
    CoinMarketDataResult result = new CoinMarketDataResult();
    try {
      String error = this.parseStatus(apiResponse.getResponseBody().get("status"));
      if (error != null) {
        result.setStatus(ResultStatus.FAILURE);
        result.setErrorMessage(error);
        return result;
      }

      result.setStatus(ResultStatus.SUCCESS);
      result.setCoinMarketDataDTOS(
          this.parseMarketDataHistoricalQuote(apiResponse.getResponseBody().get("data")));
      return result;
    } catch (Exception e) {
      log.error(e.getMessage());
      result.setStatus(ResultStatus.FAILURE);
      result.setErrorMessage(e.getMessage());
      return result;
    }
  }

  private ArrayList<CoinMarketDataDto> parseMarketDataHistoricalQuote(JsonNode data)
      throws ParseException {
    ArrayList<CoinMarketDataDto> result = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, ObjectNode> responseBodyConverted = mapper.convertValue(data,
        new TypeReference<>() {
        });
    for (Map.Entry<String, ObjectNode> coin :
        responseBodyConverted.entrySet()) {
      String name = coin.getKey();
      String symbol = coin.getValue().get("symbol").asText();
      List<QuoteDto> quoteDtoList = new ArrayList<>();
      long lastUpdated = 0;
      Map<String, ObjectNode> quotesConverted = mapper.convertValue(
          coin.getValue().get("quotes").get(0).get("quote"), new TypeReference<>() {
          });
      for (Map.Entry<String, ObjectNode> quote : quotesConverted.entrySet()) {
        var currentPrice = (float) quote.getValue().get("price").asDouble();
        var timestamp = TimeFormatter.convertStringToTimestamp(
                quote.getValue().get("timestamp").asText(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .getTime();
        lastUpdated = timestamp;
        quoteDtoList.add(new QuoteDto(quote.getKey(), currentPrice, new ArrayList<>(), timestamp));
      }
      result.add(new CoinMarketDataDto(
          name, symbol, quoteDtoList, lastUpdated));
    }
    return result;
  }
}
