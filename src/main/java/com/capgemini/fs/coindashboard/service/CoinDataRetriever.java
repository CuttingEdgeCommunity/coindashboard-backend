package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import com.capgemini.fs.coindashboard.utilDataTypes.MarketCapAndTime;
import java.util.ArrayList;

public interface CoinDataRetriever {
  static ArrayList<Coin> getCoinData() {
    ArrayList<Coin> result = new ArrayList<>();
    ArrayList<MarketCapAndTime> firstCoin = new ArrayList<>();
    firstCoin.add(new MarketCapAndTime(12, 12.5));

    result.add(new Coin("BTC", firstCoin));
    return result;
  }
}
