package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
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
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.ParseException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapResponseParser {

  private static final Logger log = LogManager.getLogger(CoinMarketCapResponseParser.class);

  public String parseStatus(JsonNode status) {
    if (status.get("error_code").asInt() != 0) {
      return status.get("error_message").asText();
    }
    return null;
  }

  public CoinMarketDataResult parseQuoteLatestResult(JsonNode apiResponseBody) {
    CoinMarketDataResult result = new CoinMarketDataResult();
    try {
      String error = this.parseStatus(apiResponseBody.get("status"));
      if (error != null) {
        result.setErrorMessage(error);
        result.setStatus(false);
        return result;
      }

      result.setStatus(ResultStatus.SUCCESS);
      result.setCoinMarketDataDTOS(this.parseCoinsQuoteLatestResult(apiResponseBody.get("data")));
      return result;
    } catch (Exception e) {
      log.error(e.getMessage());
      result.setErrorMessage(e.getMessage());
      result.setStatus(false);
      return result;
    }
  }

  public ArrayList<CoinMarketDataDto> parseCoinsQuoteLatestResult(JsonNode data)
      throws ParseException {
    ArrayList<CoinMarketDataDto> result = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, JsonNode> responseBodyConverted =
        mapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, JsonNode> coin : responseBodyConverted.entrySet()) {
      if (coin.getValue().size() == 0) {
        continue;
      }
      if (coin.getValue().getNodeType()
          == JsonNodeType
              .ARRAY) // For some reason cmc sometimes returns an array of coins for a symbol
      {
        coin = new SimpleEntry<>(coin.getKey(), coin.getValue().get(0));
      }
      String name = coin.getKey();
      String symbol = coin.getValue().get("symbol").asText();

      Map<String, QuoteDto> quoteDtos = new LinkedHashMap<>();
      Map<String, ObjectNode> quotesConverted =
          mapper.convertValue(coin.getValue().get("quote"), new TypeReference<>() {});

      for (Map.Entry<String, ObjectNode> quote : quotesConverted.entrySet()) {
        var currentPrice = quote.getValue().get("price").asDouble();
        var marketCap = quote.getValue().get("market_cap").asDouble();
        var volumeOneDay = quote.getValue().get("volume_24h").asDouble();
        var timestamp =
            TimeFormatter.convertStringToTimestamp(
                    quote.getValue().get("last_updated").asText(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .getTime();
        var pctChange1h = quote.getValue().get("percent_change_1h").asDouble();
        var pctChange24h = quote.getValue().get("percent_change_24h").asDouble();
        var pctChange7d = quote.getValue().get("percent_change_7d").asDouble();
        var pctChange30d = quote.getValue().get("percent_change_30d").asDouble();
        var nominalChange1h = this.calculateNominalDelta(currentPrice, pctChange1h);
        var nominalChange24h = this.calculateNominalDelta(currentPrice, pctChange24h);
        var nominalChange7d = this.calculateNominalDelta(currentPrice, pctChange7d);
        var nominalChange30d = this.calculateNominalDelta(currentPrice, pctChange30d);
        List<DeltaDto> deltas =
            new ArrayList<>() {
              {
                add(new DeltaDto(IntervalEnum.ONE_HOUR, pctChange1h, nominalChange1h));
                add(new DeltaDto(IntervalEnum.ONE_DAY, pctChange24h, nominalChange24h));
                add(new DeltaDto(IntervalEnum.SEVEN_DAY, pctChange7d, nominalChange7d));
                add(new DeltaDto(IntervalEnum.THIRTY_DAY, pctChange30d, nominalChange30d));
              }
            };

        quoteDtos.put(
            quote.getKey(),
            new QuoteDto(
                currentPrice, marketCap, volumeOneDay, new ArrayList<>(), deltas, timestamp));
      }
      result.add(new CoinMarketDataDto(name, symbol, quoteDtos));
    }
    return result;
  }

  public CoinMarketDataResult parseQuoteHistoricalResult(JsonNode apiResponseBody) {
    CoinMarketDataResult result = new CoinMarketDataResult();
    try {
      String error = this.parseStatus(apiResponseBody.get("status"));
      if (error != null) {
        result.setErrorMessage(error);
        result.setStatus(false);
        return result;
      }

      result.setCoinMarketDataDTOS(
          this.parseCoinsQuoteHistoricalResult(apiResponseBody.get("data")));
      result.setStatus(false);
      return result;
    } catch (Exception e) {
      log.error(e.getMessage());
      result.setStatus(ResultStatus.FAILURE);
      result.setErrorMessage(e.getMessage());
      return result;
    }
  }

  public ArrayList<CoinMarketDataDto> parseCoinsQuoteHistoricalResult(JsonNode data)
      throws ParseException {
    ArrayList<CoinMarketDataDto> result = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, ObjectNode> responseBodyConverted =
        mapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, ObjectNode> coin : responseBodyConverted.entrySet()) {
      if (coin.getValue().size() == 0) {
        continue;
      }
      String name = coin.getKey();
      String symbol = coin.getValue().get("symbol").asText();
      Map<String, QuoteDto> quoteDtos = new LinkedHashMap<>();
      List<ObjectNode> quotesConverted =
          mapper.convertValue(coin.getValue().get("quotes"), new TypeReference<>() {
          });
      for (ObjectNode quote : quotesConverted) {
        Map<String, ObjectNode> quoteConverted =
            mapper.convertValue(quote.get("quote"), new TypeReference<>() {
            });
        for (Map.Entry<String, ObjectNode> singleQuote : quoteConverted.entrySet()) {
          var price = singleQuote.getValue().get("price").asDouble();
          var timestamp =
              TimeFormatter.convertStringToTimestamp(
                      singleQuote.getValue().get("timestamp").asText(),
                      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                  .getTime();
          if (quoteDtos.containsKey(singleQuote.getKey())) {
            quoteDtos
                .get(singleQuote.getKey())
                .getPriceHistory()
                .add(0, new PriceDto(price, timestamp));
          } else {
            quoteDtos.put(
                singleQuote.getKey(),
                new QuoteDto(
                    new LinkedList<>() {
                      {
                        push(new PriceDto(price, timestamp));
                      }
                    }));
          }
        }
      }
      result.add(new CoinMarketDataDto(name, symbol, quoteDtos));
    }
    return result;
  }

  public double calculateNominalDelta(double current, double deltaPct) {
    return current - (current / (1 + deltaPct / 100));
  }
}
