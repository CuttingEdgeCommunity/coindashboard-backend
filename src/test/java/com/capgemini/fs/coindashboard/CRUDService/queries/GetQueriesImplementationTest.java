package com.capgemini.fs.coindashboard.CRUDService.queries;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {GetQueriesImplementation.class, MongoTemplate.class})
class GetQueriesImplementationTest {
  @Autowired private GetQueries getQueries;
  @MockBean private MongoTemplate mongoTemplate;
  private final String vs_currency = "usd";

  final AggregationResults mockResults = mock(AggregationResults.class);

  @Test
  public void getCoinMarketDataTestIfResultIsEmpty() {

    List<Object> result = new ArrayList<>();

    when(mockResults.getMappedResults()).thenReturn(result);
    doReturn(mockResults)
        .when(mongoTemplate)
        .aggregate(Mockito.any(Aggregation.class), Mockito.eq("Coin"), Mockito.eq(Object.class));

    assertNull(getQueries.getCoinMarketData("btc", vs_currency));
  }

  @Test
  public void getCoinMarketDataTestIfResultIsNotEmpty() {

    List<Object> result = new ArrayList<>();
    Coin coin = new Coin("1234", "BLABLA", "btc", 1, "", 123L, false, null, null, null, null);
    result.add(coin);

    when(mockResults.getMappedResults()).thenReturn(result);
    doReturn(mockResults)
        .when(mongoTemplate)
        .aggregate(Mockito.any(Aggregation.class), Mockito.eq("Coin"), Mockito.eq(Object.class));

    assertEquals(
        getQueries.getCoinMarketData("btc", vs_currency),
        "[{\"id\":\"1234\",\"name\":\"BLABLA\",\"symbol\":\"btc\",\"marketCapRank\":1,\"image_url\":\"\",\"genesis_date\":123,\"is_token\":false}]");
  }

  @Test
  public void getCoinDetailsTestIfResultIsEmpty() {

    List<Object> result = new ArrayList<>();

    when(mockResults.getMappedResults()).thenReturn(result);
    doReturn(mockResults)
        .when(mongoTemplate)
        .aggregate(Mockito.any(Aggregation.class), Mockito.eq("Coin"), Mockito.eq(Object.class));

    assertNull(getQueries.getCoinDetails("btc"));
  }

  @Test
  public void getCoinDetailsTestIfResultIsNotEmpty() {

    List<Object> result = new ArrayList<>();
    Coin coin = new Coin("1234", "BLABLA", "btc", 1, "", 123L, false, null, null, null, null);
    result.add(coin);

    when(mockResults.getMappedResults()).thenReturn(result);
    doReturn(mockResults)
        .when(mongoTemplate)
        .aggregate(Mockito.any(Aggregation.class), Mockito.eq("Coin"), Mockito.eq(Object.class));

    assertEquals(
        getQueries.getCoinDetails("btc"),
        "[{\"id\":\"1234\",\"name\":\"BLABLA\",\"symbol\":\"btc\",\"marketCapRank\":1,\"image_url\":\"\",\"genesis_date\":123,\"is_token\":false}]");
  }

  @Test
  public void getCoinsTestIfResultIsEmpty() {

    List<Object> result = new ArrayList<>();

    when(mockResults.getMappedResults()).thenReturn(result);
    doReturn(mockResults)
        .when(mongoTemplate)
        .aggregate(Mockito.any(Aggregation.class), Mockito.eq("Coin"), Mockito.eq(Object.class));

    assertNull(getQueries.getCoins(1, 0));
  }
}
