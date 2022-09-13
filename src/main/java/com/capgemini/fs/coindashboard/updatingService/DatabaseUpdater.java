package com.capgemini.fs.coindashboard.updatingService;

import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.UpdateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@Setter
public class DatabaseUpdater {

  private final int MAX_COINS = 250;
  private boolean enabled = true;

  @Autowired private UpdateQueries updateQueries;
  @Autowired private GetQueries getQueries;
  @Autowired private CreateQueries createQueries;
  @Autowired private ApiHolder apiHolder;

  // Updating current market data for top 250 coins each few seconds.
  @Async
  @Scheduled(fixedDelay = 10000)
  public boolean currentQuoteUpdates() {
    if (this.enabled) {
      List<String> vsCurrencies = new ArrayList<>();
      vsCurrencies.add("usd");
      var result = this.apiHolder.getTopCoins(MAX_COINS, 0, vsCurrencies);
      try {
        updateQueries.updateTopCoinsTransaction(result.orElseThrow());
        return true;
      } catch (Exception ex) {
        log.error(ex.getMessage());
        return false;
      }
    }
    return false;
  }

  @Async
  @Scheduled(cron = "* */5 * * * *")
  public boolean chartUpdate() {
    if (this.enabled) {
      this.updateQueries.UpdateEveryCoinPriceChart();
      return true;
    }
    return false;
  }
}
