package com.capgemini.fs.coindashboard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoinTest {
  MarketCapAndTime x = new MarketCapAndTime(10L, 9.0);
  MarketCapAndTime y = new MarketCapAndTime(10L, 9.0);
  ArrayList<MarketCapAndTime> list = new ArrayList<MarketCapAndTime>();
  private Coin tester;

  @BeforeEach
  void setup() {

    tester = new Coin("Bitcoin", list);
  }

  @Test
  public ArrayList<MarketCapAndTime> setHistoricalData() {
    list.add(x);
    list.add(y);
    return list;
  }

  @Test
  void getId() {
    System.out.println("Testing getId");
    tester.setId(5L);
    Long response = tester.getId();
    assertEquals(5L, response);
  }

  @Test
  void getName() {
    System.out.println("Testing getName");
    tester.setName("Bitcoin");
    String response = tester.getName();
    assertEquals("Bitcoin", response);
  }

  @Test
  void getHistoricalData() {
    System.out.println("Testing getHistoricalData");
    setHistoricalData();
  }

  @Test
  void testToString() {
    tester.setId(5L);
    tester.setName("Bitcoin");
    MarketCapAndTime m = new MarketCapAndTime(10L, 9.0);
    MarketCapAndTime g = new MarketCapAndTime(10L, 9.0);
    ArrayList<MarketCapAndTime> TestList = new ArrayList<MarketCapAndTime>();
    TestList.add(m);
    TestList.add(g);
    Long id = tester.getId();
    String name = tester.getName();
    assertEquals("Bitcoin", name);
    assertEquals(5L, id);
    assertEquals(TestList, setHistoricalData());
  }

  @Test
  void testEqualsAndHashCode() {
    Assertions.assertTrue(x.equals(y) && y.equals(x));
    assertEquals(x.hashCode(), y.hashCode());
  }
}
