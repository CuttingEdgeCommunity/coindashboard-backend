package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import org.junit.jupiter.api.Test;

class PriceDtoTest {

  private final PriceDto priceA = new PriceDto(123d, 123L);
  private final PriceDto priceB = new PriceDto(13d, 123L);
  private final PriceDto priceC = new PriceDto(123d, 123L);

  @Test
  void testEquals() {
    assertEquals(this.priceA, this.priceC);
    assertNotEquals(this.priceA, this.priceB);
  }

  @Test
  void testHashCode() {
    assertEquals(this.priceA.hashCode(), this.priceC.hashCode());
    assertNotEquals(this.priceA.hashCode(), this.priceB.hashCode());
  }

  @Test
  void testToString() {
    assertEquals("PriceDto(price=123.0, timestamp=123)", this.priceA.toString());
  }
}
