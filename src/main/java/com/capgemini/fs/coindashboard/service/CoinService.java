package com.capgemini.fs.coindashboard.service;

import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import java.util.ArrayList;

public interface CoinService {
  ArrayList<Coin> getCoins();

  Coin getCoinById(Integer id);
}
