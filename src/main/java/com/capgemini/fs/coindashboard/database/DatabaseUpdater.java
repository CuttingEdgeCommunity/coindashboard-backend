package com.capgemini.fs.coindashboard.database;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.database.queries.GetQueries;
import com.capgemini.fs.coindashboard.database.queries.UpdateQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUpdater {

  private boolean enabled = false;

  @Autowired
  private GetQueries getQueries;
  @Autowired
  private UpdateQueries updateQueries;
  @Autowired
  private ApiHolder apiHolder;

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Async
  @Scheduled(fixedDelay = 1500)
  public void singleCoinUpdater() {
    if (this.enabled) {
      var coinMarketData = this.apiHolder.getCoinMarketData("btc");
    }
  }
}
