package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.capgemini.fs.coindashboard.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum;
import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class QuoteDtoTest {

  private final QuoteDto quoteA =
      new QuoteDto(
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
  private final QuoteDto quoteB =
      new QuoteDto(
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
  private final QuoteDto quoteC =
      new QuoteDto(
          new ArrayList<>() {
            {
              add(new PriceDto(13d, 123L));
            }
          },
          new ArrayList<>() {
            {
              add(new DeltaDto(IntervalEnum.ONE_HOUR, 123d, 123d));
            }
          },
          1233L);

  @Test
  void testEquals() {
    assertEquals(this.quoteA, this.quoteB);
    assertNotEquals(this.quoteA, this.quoteC);
  }

  @Test
  void testHashCode() {
    assertEquals(this.quoteA.hashCode(), this.quoteB.hashCode());
    assertNotEquals(this.quoteA.hashCode(), this.quoteC.hashCode());
  }

  @Test
  void testToString() {
    assertEquals(
        "QuoteDto(prices=[PriceDto(price=123.0, timestamp=123)],"
            + " deltas=[DeltaDto(interval=ONE_HOUR, percentChange=123.0, nominalChange=123.0)],"
            + " lastUpdateTimestampMillis=1233)",
        this.quoteB.toString());
  }
}
