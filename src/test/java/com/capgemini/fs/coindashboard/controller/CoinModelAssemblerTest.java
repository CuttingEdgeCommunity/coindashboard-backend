package com.capgemini.fs.coindashboard.controller;

import com.capgemini.fs.coindashboard.utilDataTypes.Coin;
import com.capgemini.fs.coindashboard.utilDataTypes.MarketCapAndTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoinModelAssemblerTest {
  private CoinModelAssembler assembler = new CoinModelAssembler();
  private ArrayList<MarketCapAndTime> firstCoin = new ArrayList<>();

  @Test
  public void IsCoinCorrectlyAssembled() {
    firstCoin.add(new MarketCapAndTime(10, 3));
    Coin coin = new Coin("BTC", firstCoin);

    Assertions.assertEquals(
        assembler.toModel(coin).toString(),
        "EntityModel { content: Coins{id=null, name='BTC', historicalData=[{time=10,"
            + " marketCap=3.0}]}, links: [</coins/BTC>;rel=\"self\", </coins>;rel=\"coins\"] }");
  }
}
