package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.TimeFormatter;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum;
import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoinGeckoResponseParser {

  ArrayList<CoinMarketDataDto> CurrentParser(Response response, String currency) {
    ArrayList<CoinMarketDataDto> coinMarketDataDtos = new ArrayList<>();
    List<DeltaDto> deltas;
    List<PriceDto> prices;
    Map<String, QuoteDto> quoteMaps;
    long timestamp = 0;
    double pctChange;
    for (int i = 0; i < response.getResponseBody().size(); i++) {
      deltas = new ArrayList<>();
      prices = new ArrayList<>();
      quoteMaps = new HashMap<>();
      try {
        timestamp =
            TimeFormatter.convertStringToTimestamp(
                    response.getResponseBody().get(i).get("last_updated").asText(),
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .getTime();
      } catch (ParseException ignored) {
      }
      double current_price = response.getResponseBody().get(i).get("current_price").asDouble();
      long marketCap = response.getResponseBody().get(i).get("market_cap").asLong();
      long volumeOneDay = response.getResponseBody().get(i).get("total_volume").asLong();
      prices.add(new PriceDto(current_price, timestamp));
      pctChange =
          response
              .getResponseBody()
              .get(i)
              .get("price_change_percentage_1h_in_currency")
              .asDouble();
      deltas.add(
          new DeltaDto(
              IntervalEnum.ONE_HOUR, pctChange, current_price * (pctChange / (100 + pctChange))));
      pctChange =
          response
              .getResponseBody()
              .get(i)
              .get("price_change_percentage_24h_in_currency")
              .asDouble();
      deltas.add(
          new DeltaDto(
              IntervalEnum.ONE_DAY, pctChange, current_price * (pctChange / (100 + pctChange))));
      pctChange =
          response
              .getResponseBody()
              .get(i)
              .get("price_change_percentage_7d_in_currency")
              .asDouble();
      deltas.add(
          new DeltaDto(
              IntervalEnum.SEVEN_DAY, pctChange, current_price * (pctChange / (100 + pctChange))));
      pctChange =
          response
              .getResponseBody()
              .get(i)
              .get("price_change_percentage_30d_in_currency")
              .asDouble();
      deltas.add(
          new DeltaDto(
              IntervalEnum.THIRTY_DAY, pctChange, current_price * (pctChange / (100 + pctChange))));
      quoteMaps.put(currency, new QuoteDto(current_price, marketCap, volumeOneDay, prices, deltas, timestamp));
      coinMarketDataDtos.add(
          new CoinMarketDataDto(
              response.getResponseBody().get(i).get("name").asText(),
              response.getResponseBody().get(i).get("symbol").asText(),
              quoteMaps));
    }
    return coinMarketDataDtos;
  }

  CoinMarketDataDto HistoricalParser(Response response, String name, String vs_currency) {
    CoinMarketDataDto coinMarketDataDto;
//  List<DeltaDto> deltas = new ArrayList<>();
    List<PriceDto> prices = new ArrayList<>();
    Map<String, QuoteDto> quoteMaps = new LinkedHashMap<>();
    for (int i = 0; i < response.getResponseBody().get("prices").size(); i++) {
      prices.add(
          new PriceDto(
              response.getResponseBody().get("prices").get(i).get(1).floatValue(),
              response.getResponseBody().get("prices").get(i).get(0).asLong()));
    }
    quoteMaps.put(vs_currency, new QuoteDto(prices));
//            deltas,
//            response
//                .getResponseBody()
//                .get("prices")
//                .get(response.getResponseBody().get("prices").size() - 1)
//                .get(0)
//                .asLong()));
    coinMarketDataDto = new CoinMarketDataDto(name,quoteMaps);
    return coinMarketDataDto;
  }
}
