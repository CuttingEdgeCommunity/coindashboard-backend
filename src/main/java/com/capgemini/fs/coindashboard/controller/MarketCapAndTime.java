package com.capgemini.fs.coindashboard.controller;
import java.io.Serializable;
import java.util.Objects;

public class MarketCapAndTime implements Serializable{
    private long time;
    private double marketCap;


  public MarketCapAndTime(long time, double marketCap) {
    this.time = time;
    this.marketCap = marketCap;
  }

  public double getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(double marketCap) {
    this.marketCap = marketCap;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "{" +
        "time=" + time +
        ", marketCap=" + marketCap +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof MarketCapAndTime))
      return false;
    MarketCapAndTime that = (MarketCapAndTime) o;
    return getTime() == that.getTime() && Double.compare(that.getMarketCap(), getMarketCap()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTime(), getMarketCap());
  }
}
