package com.capgemini.fs.coindashboard.database;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.database.queries.UpdateQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUpdater {

  private boolean enabled = false;

  @Autowired
  private UpdateQueries updateQueries;
  @Autowired
  private ApiHolder apiHolder;

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Async
  @Scheduled(fixedDelay = 1500)
  public void singleCoinUpdate() {
    if (this.enabled) {
      var coinMarketData = this.apiHolder.getCoinMarketData("btc");
      if (coinMarketData != null) {
        this.updateQueries.UpdateCoinCurrentQuote(
            "bitcoin", coinMarketData.getCoinMarketDataDTOS().get(0).getQuoteMap().get("usd"));
      }
    }
  }

  @Async
  @Scheduled(cron = "*/5 * * * *")
  public void chartUpdate() {
    if (this.enabled) {
      this.updateQueries.UpdateEveryCoinPriceChart();
    }
  }
}
