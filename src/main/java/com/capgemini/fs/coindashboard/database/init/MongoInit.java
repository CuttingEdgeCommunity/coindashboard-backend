package com.capgemini.fs.coindashboard.database.init;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.database.queries.CreateQueries;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

//class for initialization of a mongoDB with historical data of coins
public class MongoInit implements CommandLineRunner {
  @Autowired
  private CoinMarketDataResult coinMarketDataDto;
  @Autowired
  private CreateQueries createQueries;
  @Autowired
  ApiHolder apiholder;

  public static void main(String[] args){
    SpringApplication.run(MongoInit.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    coinMarketDataDto = apiholder.getCoinMarketData("btc");
    //createQueries.CreateCoinDocument(coinMarketDataDto);
  }
}
