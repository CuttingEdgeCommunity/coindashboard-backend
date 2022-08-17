package com.capgemini.fs.coindashboard.database.queries.Utils;

import com.capgemini.fs.coindashboard.database.model.Coin;
import com.capgemini.fs.coindashboard.database.model.CoinBuilder;
import com.capgemini.fs.coindashboard.database.model.CurrentQuote;
import com.capgemini.fs.coindashboard.database.model.CurrentQuoteBuilder;
import com.capgemini.fs.coindashboard.database.model.Delta;
import com.capgemini.fs.coindashboard.database.model.DeltaBuilder;
import com.capgemini.fs.coindashboard.database.model.Price;
import com.capgemini.fs.coindashboard.database.model.PriceBuilder;
import com.capgemini.fs.coindashboard.database.model.Quote;
import com.capgemini.fs.coindashboard.database.model.QuoteBuilder;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.util.ArrayList;
import java.util.List;
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
    List<Price> chart = new ArrayList<>();
    for (DeltaDto deltaDto : quoteDto.getDeltas()) {
      deltas.add(fromDeltaDtoToDelta(deltaDto));
    }
    for (PriceDto priceDto : quoteDto.getPrices()) {
      chart.add(fromPriceDtoToPrice(priceDto));
    }

    return CurrentQuoteBuilder.aCurrentQuote().withDeltas(deltas).withChart(chart).build();
  }

  public static Coin fromCoinMarketDataDtoToCoin(CoinMarketDataDto coinMarketDataDto) {
    List<Quote> quotes = new ArrayList<>();
    for (Entry<String, QuoteDto> quote : coinMarketDataDto.getQuoteMap().entrySet()) {
      quotes.add(
          QuoteBuilder.aQuote()
              .withVs_currency(quote.getKey())
              .withCurrentQuote(fromQuoteDtoToCurrentQuote(quote.getValue()))
              .build());
    }

    return CoinBuilder.aCoin()
        .withName(coinMarketDataDto.getName())
        .withSymbol(coinMarketDataDto.getSymbol())
        .withQuotes(quotes)
        .build();
  }
}
