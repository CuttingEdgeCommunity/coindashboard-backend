package com.capgemini.fs.coindashboard.updatingService;

import com.capgemini.fs.coindashboard.CRUDService.queries.UpdateQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import java.util.List;
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
  public void singleCoinUpdate() {
    if (this.enabled) {
      var result = this.apiHolder.getCurrentListing(List.of("btc"), List.of("usd"), false);
      result.ifPresent(
          value ->
              this.updateQueries.UpdateCoinCurrentQuote(
                  "Bitcoin",
                  value.getCoins().get(0).getQuotes().get("usd").getCurrentQuote(),
                  "usd"));
    }
  }

  @Async
  @Scheduled(cron = "* */5 * * * *")
  public void chartUpdate() {
    if (this.enabled) {
      this.updateQueries.UpdateEveryCoinPriceChart();
    }
  }
}
