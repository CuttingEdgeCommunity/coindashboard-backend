package com.capgemini.fs.coindashboard.CRUDService.queries.Utils;

import com.capgemini.fs.coindashboard.CRUDService.model.builder.CoinBuilder;
import com.capgemini.fs.coindashboard.CRUDService.model.builder.CurrentQuoteBuilder;
import com.capgemini.fs.coindashboard.CRUDService.model.builder.DeltaBuilder;
import com.capgemini.fs.coindashboard.CRUDService.model.builder.PriceBuilder;
import com.capgemini.fs.coindashboard.CRUDService.model.builder.QuoteBuilder;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Delta;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Price;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Passers {

  public static Delta fromDeltaDtoToDelta(DeltaDto deltaDto) {
    return DeltaBuilder.aDelta()
        .withInterval(deltaDto.getInterval().toString())
        .withNominal(deltaDto.getNominalChange())
        .withPct(deltaDto.getPercentChange())
        .build();
  }

  public static Price fromPriceDtoToPrice(PriceDto priceDto) {
    return PriceBuilder.aPrice()
        .withPrice(priceDto.getPrice())
        .withTimestamp(priceDto.getTimestamp())
        .build();
  }

  public static CurrentQuote fromQuoteDtoToCurrentQuote(QuoteDto quoteDto) {
    List<Delta> deltas = new ArrayList<>();
    for (DeltaDto deltaDto : quoteDto.getDeltas()) {
      deltas.add(fromDeltaDtoToDelta(deltaDto));
    }

    return CurrentQuoteBuilder.aCurrentQuote()
        .withLast_update(quoteDto.getLastUpdateTimestampMillis())
        .withDeltas(deltas)
        .withMarket_cap(quoteDto.getMarketCap())
        .withPrice(quoteDto.getCurrentPrice())
        .withDaily_volume(quoteDto.getVolumeOneDay())
        .build();
  }

  public static Coin fromCoinMarketDataDtoToCoin(CoinMarketDataDto coinMarketDataDto) {
    Map<String, Quote> quotes = new HashMap<>();
    for (Entry<String, QuoteDto> quote : coinMarketDataDto.getQuoteMap().entrySet()) {
      List<Price> chart = new ArrayList<>();
      for (PriceDto priceDto : quote.getValue().getPriceHistory()) {
        chart.add(fromPriceDtoToPrice(priceDto));
      }
      quotes.put(
          quote.getKey(),
          QuoteBuilder.aQuote()
              .withCurrentQuote(fromQuoteDtoToCurrentQuote(quote.getValue()))
              .withChart(chart)
              .withVs_currency(quote.getKey())
              .build());
    }

    return CoinBuilder.aCoin()
        .withName(coinMarketDataDto.getName())
        .withSymbol(coinMarketDataDto.getSymbol())
        .withQuotes(quotes)
        .build();
  }
}
