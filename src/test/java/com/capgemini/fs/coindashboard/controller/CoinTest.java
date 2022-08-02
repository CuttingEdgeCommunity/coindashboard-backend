package com.capgemini.fs.coindashboard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CoinTest {
  MarketCapAndTime x;
  MarketCapAndTime y;
  ArrayList<MarketCapAndTime> list = new ArrayList<MarketCapAndTime>();
  private Coin tester;
@BeforeEach
void setup(){
  x = new MarketCapAndTime(10L, 9.0);
  y = new MarketCapAndTime(10L, 9.0);
  list.add(x);
  list.add(y);
  tester = new Coin("Bitcoin",list);
}
  @Test
  void testToString() {
    tester.setId(5L);
    tester.setName("Bitcoin");
    String expected = "Coins{id=5, name='Bitcoin', historicalData=[{time=10, marketCap=9.0}, {time=10, marketCap=9.0}]}";
    Assertions.assertEquals(expected, tester.toString());
  }

  @Test
  void testEqualsAndHashCode() {
  Assertions.assertTrue(x.equals(y) && y.equals(x));
  assertEquals(x.hashCode(), y.hashCode());
  }

}
