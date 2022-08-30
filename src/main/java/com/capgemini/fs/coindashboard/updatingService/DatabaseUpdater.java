package com.capgemini.fs.coindashboard.updatingService;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
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

  private boolean enabled = true;

  @Autowired private UpdateQueries updateQueries;
  @Autowired private GetQueries getQueries;
  @Autowired private CreateQueries createQueries;
  @Autowired private ApiHolder apiHolder;

  @Async
  private void singleCoinUpdate(Coin coin, String vs_currency) {
    if (!getQueries.isCoinInDBBySymbol(coin.getSymbol())) {
      List<String> symbols = new ArrayList<>();
      symbols.add(coin.getSymbol());
      var result = this.apiHolder.getCoinInfo(symbols);
      Coin coinWithDetails = result.orElseThrow().getCoins().get(0);
      coin.setContract_address(coinWithDetails.getContract_address());
      coin.setDescription(coinWithDetails.getDescription());
      coin.setGenesis_date(coinWithDetails.getGenesis_date());
      coin.setImage_url(coinWithDetails.getImage_url());
      coin.setIs_token(coinWithDetails.getIs_token());
      coin.setLinks(coinWithDetails.getLinks());
      createQueries.CreateCoinDocument(coin);
      log.info("new coin on the list");
    } else {
      updateQueries.UpdateCoinCurrentQuote(
          coin.getSymbol(), coin.getQuotes().get(vs_currency).getCurrentQuote(), vs_currency);
    }
  }

  @Async
  @Scheduled(fixedDelay = 10000)
  public void currentQuoteUpdates() {
    if (this.enabled) {
      List<String> vsCurrencies = new ArrayList<>();
      vsCurrencies.add("usd");
      var result = this.apiHolder.getTopCoins(250, 0, vsCurrencies);
      List<Coin> coins = result.orElseThrow().getCoins();
      for (Coin coin : coins) {
        singleCoinUpdate(coin, "usd");
      }
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
