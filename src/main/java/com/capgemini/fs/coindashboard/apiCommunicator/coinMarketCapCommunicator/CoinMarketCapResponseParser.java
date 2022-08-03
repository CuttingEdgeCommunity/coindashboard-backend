package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.IntervalEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.QuoteDto;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoinMarketCapResponseParser {

  public ArrayList<CoinMarketDataDto> parseGetCoinsQuoteResult(JsonNode responseBody)
      throws ParseException {
    ArrayList<CoinMarketDataDto> result = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, ObjectNode> responseBodyConverted = mapper.convertValue(responseBody.get("data"),
        new TypeReference<>() {
        });
    for (Map.Entry<String, ObjectNode> coin :
        responseBodyConverted.entrySet()) {
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
}
