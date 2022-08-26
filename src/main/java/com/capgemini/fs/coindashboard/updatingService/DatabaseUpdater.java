package com.capgemini.fs.coindashboard.updatingService;

import com.capgemini.fs.coindashboard.CRUDService.queries.UpdateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Setter
public class DatabaseUpdater {

  private boolean enabled = true;

  @Autowired private UpdateQueries updateQueries;
  @Autowired private ApiHolder apiHolder;

  @Async
  @Scheduled(fixedDelay = 3070)
  public boolean singleCoinUpdate() {
    if (!this.enabled) return false;

    var coinMarketData = this.apiHolder.getCoinMarketData("btc");
    if (coinMarketData == null) return false;

    return this.updateQueries.updateCoinCurrentQuote(
        "Bitcoin",
        coinMarketData.getCoinMarketDataDTOS().get(0).getQuoteMap().get("usd"),
        "usd");

  }

  @Async
  @Scheduled(cron = "* */5 * * * *")
  public boolean chartUpdate() {
    if (!this.enabled) return false;

    return this.updateQueries.updateEveryCoinPriceChart();
  }
}
