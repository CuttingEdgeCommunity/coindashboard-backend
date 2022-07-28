package com.capgemini.fs.coindashboard.controller;


import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coins {
  private @Id
  @GeneratedValue Long id;
  private String name;

  private ArrayList<MarketCapAndTime> historicalData;


  public Coins(){

  }
  public Coins(String name, ArrayList<MarketCapAndTime> historicalData) {
    this.name = name;
    this.historicalData = historicalData;
  }



  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public ArrayList<MarketCapAndTime> getHistoricalData() {
    return historicalData;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }


  public void setHistoricalData(ArrayList<MarketCapAndTime> historicalData) {
    this.historicalData = historicalData;
  }


  @Override
  public String toString() {
    return "Coins{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", historicalData=" + historicalData +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Coins))
      return false;
    Coins coins = (Coins) o;
    return Objects.equals(getId(), coins.getId()) && Objects.equals(getName(), coins.getName())
        && Objects.equals(getHistoricalData(), coins.getHistoricalData());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getHistoricalData());
  }
}