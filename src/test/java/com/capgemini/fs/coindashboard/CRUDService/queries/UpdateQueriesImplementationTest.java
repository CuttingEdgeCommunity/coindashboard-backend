package com.capgemini.fs.coindashboard.CRUDService.queries;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import com.mongodb.client.ClientSession;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.BsonValue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(
    classes = {
      UpdateQueriesImplementation.class,
      ClientSession.class,
      MongoTemplate.class,
      GetQueriesImplementation.class,
      CreateQueriesImplementation.class,
      ApiHolder.class
    })
class UpdateQueriesImplementationTest {
  @Autowired private UpdateQueriesImplementation updateQueries;
  @MockBean private MongoTemplate mongoTemplate;
  @MockBean private ClientSession mongoSession;

  private final String vs_currency = "usd";
  private List<String> vsCurrencies = new ArrayList<>();
  private Quote quote = new Quote(vs_currency, null, null);
  private Map<String, Quote> quotes = new HashMap<>();
  private CurrentQuote newQuote = new CurrentQuote();
  private Coin coin;
  private Integer marketCapRank;
  private List<Coin> coins = new ArrayList<>();
  private Result resultOfGetTopCoins =
      new Result(ApiProviderEnum.COIN_MARKET_CAP, ResultStatus.SUCCESS, "", coins);
  private Result resultOfGetCoinInfo =
      new Result(ApiProviderEnum.COIN_MARKET_CAP, ResultStatus.SUCCESS, "", coins);

  private UpdateResult updateResult(boolean wasAcknowledged) {
    return new UpdateResult() {
      @Override
      public boolean wasAcknowledged() {
        return wasAcknowledged;
      }

      @Override
      public long getMatchedCount() {
        return 0;
      }

      @Override
      public long getModifiedCount() {
        return 0;
      }

      @Override
      public BsonValue getUpsertedId() {
        return null;
      }
    };
  }

  @Test
  public void UpdateCoinCurrentQuoteTest() {
    Query query = new Query();
    query.addCriteria(Criteria.where("symbol").is("btc"));
    Update update = new Update();
    update.set("quotes." + vs_currency.toLowerCase() + ".currentQuote", newQuote);
    UpdateResult updateResult = updateResult(true);
    when(mongoTemplate.updateMulti(query, update, Coin.class)).thenReturn(updateResult);

    assertTrue(updateQueries.updateCoinCurrentQuote("btc", newQuote, vs_currency));
  }

  @Test
  public void UpdateCoinCurrentQuoteTestNull() {
    assertFalse(updateQueries.updateCoinCurrentQuote(null, null, null));
  }

  @Test
  public void UpdateCoinCurrentQuoteAndMarketCapRankTestNull() {
    assertFalse(updateQueries.updateCoinCurrentQuoteAndMarketCapRank(null, null, null, null));
  }

  @Test
  public void UpdateCoinsCurrentQuoteAndMarketCapRankTestForEmptyList() {
    assertTrue(updateQueries.updateCoinsCurrentQuotesAndMarketCapRank(List.of()));
  }

  @Test
  public void UpdateCoinsCurrentQuoteAndMarketCapRankTestForNewList() {
    quote.setCurrentQuote(newQuote);
    quotes.put("usd", quote);
    coin = new Coin();
    coin.setQuotes(quotes);
    assertTrue(updateQueries.updateCoinsCurrentQuotesAndMarketCapRank(List.of(coin)));
  }

  @Test
  public void UpdateCoinsCurrentQuotesTest() {
    assertTrue(
        updateQueries.updateCoinsCurrentQuotesAndMarketCapRank(resultOfGetTopCoins.getCoins()));
  }

  @Test
  @Disabled
  public void UpdateTopCoinsTransactionTest() {
    assertTrue(
        updateQueries.updateTopCoinsTransaction(
            resultOfGetTopCoins.getCoins(), List.of(), resultOfGetTopCoins.getCoins()));
  }

  @Test
  public void UpdateCoinCurrentQuoteWasNotAcknowledged() {
    Query query = new Query();
    query.addCriteria(Criteria.where("symbol").is("btc"));
    Update update = new Update();
    update.set("quotes." + vs_currency.toLowerCase() + ".currentQuote", newQuote);
    UpdateResult updateResult = updateResult(false);
    when(mongoTemplate.updateMulti(query, update, Coin.class)).thenReturn(updateResult);

    assertFalse(updateQueries.updateCoinCurrentQuote("btc", newQuote, vs_currency));
  }

  @Test
  public void UpdateCoinCurrentQuoteAndMarketCapRankWasNotAcknowledged() {
    Query query = new Query();
    query.addCriteria(Criteria.where("symbol").is("btc"));
    Update update = new Update();
    update
        .set("quotes." + vs_currency.toLowerCase() + ".currentQuote", newQuote)
        .set("marketCapRank", marketCapRank);
    UpdateResult updateResult = updateResult(false);
    when(mongoTemplate.updateMulti(query, update, Coin.class)).thenReturn(updateResult);

    assertFalse(
        updateQueries.updateCoinCurrentQuoteAndMarketCapRank(
            "btc", newQuote, vs_currency, marketCapRank));
  }

  @Test
  @Disabled // TODO repair test after refactoring
  public void CleanCoinsMarketCapRanksTest() {
    Query query = new Query(Criteria.where("rank").gte(1).lte(250));
    Update update = new Update();
    update.set("marketCapRank", Integer.MAX_VALUE);
    UpdateResult updateResult = updateResult(true);
    when(mongoTemplate.updateMulti(query, update, Coin.class)).thenReturn(updateResult);
    assertTrue(updateQueries.cleanCoinsMarketCapRanks(List.of()));
  }

  @Test
  @Disabled // TODO repair test after refactoring
  public void CleanCoinsMarketCapRanksWasNotAcknowledged() {
    Query query = new Query(Criteria.where("rank").gte(1).lte(250));
    Update update = new Update();
    update.set("marketCapRank", Integer.MAX_VALUE);
    UpdateResult updateResult = updateResult(false);
    when(mongoTemplate.updateMulti(query, update, Coin.class)).thenReturn(updateResult);
    assertFalse(updateQueries.cleanCoinsMarketCapRanks(List.of()));
  }
}
