package com.capgemini.fs.coindashboard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarketCapAndTimeTest {
  MarketCapAndTime x;
  MarketCapAndTime y;
  @BeforeEach
  void setup(){
    x = new MarketCapAndTime(2L,2.0);
    y = new MarketCapAndTime(2L,2.0);
  }
  @Test
  void testToString() {
    String expected = "{time=2"  + ", marketCap=2.0}";
    Assertions.assertEquals(expected, x.toString());
  }

  @Test
  void testEqualsAndHashCode() {
    Assertions.assertTrue(x.equals(y) && y.equals(x));
    assertEquals(x.hashCode(), y.hashCode());
  }
}