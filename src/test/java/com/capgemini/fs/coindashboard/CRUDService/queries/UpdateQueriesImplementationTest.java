package com.capgemini.fs.coindashboard.CRUDService.queries;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.BsonValue;
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
@ContextConfiguration(classes = {UpdateQueriesImplementation.class, MongoTemplate.class})
class UpdateQueriesImplementationTest {
  @Autowired private UpdateQueriesImplementation updateQueries;
  @MockBean private MongoTemplate mongoTemplate;

  private final String vs_currency = "usd";
  private List<String> vsCurrencies = new ArrayList<>();
  private Quote quote = new Quote(vs_currency, null, null);
  private Map<String, Quote> quotes = new HashMap<>();
  private CurrentQuote newQuote = new CurrentQuote();
  private Coin coin;
  private List<Coin> coins = new ArrayList<>();
  private Result resultOfGetTopCoins;
  private Result resultOfGetCoinInfo;

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
  public void UpdateCoinMarketRankCapTest() {
    Query query = new Query();
    query.addCriteria(Criteria.where("symbol").is("btc"));
    Update update = new Update();
    update.set("marketCapRank", 1);
    UpdateResult updateResult = updateResult(true);
    when(mongoTemplate.updateMulti(query, update, Coin.class)).thenReturn(updateResult);

    assertTrue(updateQueries.UpdateCoinMarketCapRank("btc", 1));
  }

  @Test
  public void UpdateCoinMarketRankCapWasNotAcknowledged() {
    Query query = new Query();
    query.addCriteria(Criteria.where("symbol").is("btc"));
    Update update = new Update();
    update.set("marketCapRank", 1);
    UpdateResult updateResult = updateResult(false);
    when(mongoTemplate.updateMulti(query, update, Coin.class)).thenReturn(updateResult);

    assertFalse(updateQueries.UpdateCoinMarketCapRank("btc", 1));
  }
}
