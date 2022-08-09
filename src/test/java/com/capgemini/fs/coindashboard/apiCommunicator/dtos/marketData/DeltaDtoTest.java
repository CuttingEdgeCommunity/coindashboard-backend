package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.capgemini.fs.coindashboard.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum;
import org.junit.jupiter.api.Test;

class DeltaDtoTest {

  private final DeltaDto deltaA = new DeltaDto(IntervalEnum.ONE_HOUR, 123d, 321d);
  private final DeltaDto deltaB = new DeltaDto(IntervalEnum.ONE_HOUR, 123d, 321d);
  private final DeltaDto deltaC = new DeltaDto(IntervalEnum.THIRTY_DAY, 123d, 321d);

  @Test
  void testEquals() {
    assertEquals(deltaA, deltaB);
    assertNotEquals(deltaA, deltaC);
  }

  @Test
  void testHashCode() {
    assertEquals(deltaA.hashCode(), deltaB.hashCode());
    assertNotEquals(deltaA.hashCode(), deltaC.hashCode());
  }

  @Test
  void testToString() {
    assertEquals(
        "DeltaDto(interval=ONE_HOUR, percentChange=123.0, nominalChange=321.0)", deltaA.toString());
  }
}
