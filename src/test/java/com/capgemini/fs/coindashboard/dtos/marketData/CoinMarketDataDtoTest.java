package com.capgemini.fs.coindashboard.dtos.marketData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;

class CoinMarketDataDtoTest {

  private final QuoteDto quote =
      new QuoteDto(
          123d,
          123d,
          123d,
          new ArrayList<>() {
            {
              add(new PriceDto(123d, 123L));
            }
          },
          new ArrayList<>() {
            {
              add(new DeltaDto(IntervalEnum.ONE_HOUR, 123d, 123d));
            }
          },
          1233L);
  private final CoinMarketDataDto coinMarketDataA =
      new CoinMarketDataDto(
          "1",
          "11",
          new LinkedHashMap<>() {
            {
              put("usd", quote);
            }
          });
  private final CoinMarketDataDto coinMarketDataB =
      new CoinMarketDataDto(
          "1",
          "11",
          new LinkedHashMap<>() {
            {
              put("usd", quote);
            }
          });
  private final CoinMarketDataDto coinMarketDataC =
      new CoinMarketDataDto(
          "1",
          "11",
          new LinkedHashMap<>() {
            {
              put("udsd", quote);
            }
          });

  @Test
  void testEquals() {
    assertEquals(this.coinMarketDataA, this.coinMarketDataB);
    assertNotEquals(this.coinMarketDataA, this.coinMarketDataC);
  }

  @Test
  void testHashCode() {
    assertEquals(this.coinMarketDataA.hashCode(), this.coinMarketDataB.hashCode());
    assertNotEquals(this.coinMarketDataA.hashCode(), this.coinMarketDataC.hashCode());
  }

  @Test
  void testToString() {
    assertEquals(
        "CoinMarketDataDto(name=1, symbol=11, quoteMap={usd=QuoteDto(currentPrice=123.0,"
            + " marketCap=123.0, volumeOneDay=123.0, priceHistory=[PriceDto(price=123.0,"
            + " timestamp=123)], deltas=[DeltaDto(interval=ONE_HOUR, percentChange=123.0,"
            + " nominalChange=123.0)], lastUpdateTimestampMillis=1233)})",
        this.coinMarketDataA.toString());
  }
}
