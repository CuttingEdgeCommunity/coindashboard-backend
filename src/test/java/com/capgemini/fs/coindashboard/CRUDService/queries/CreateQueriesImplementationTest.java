package com.capgemini.fs.coindashboard.CRUDService.queries;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {CreateQueriesImplementation.class, MongoTemplate.class})
class CreateQueriesImplementationTest {

  @Autowired private CreateQueriesImplementation createQueries;

  @MockBean private MongoTemplate mongoTemplate;

  @Test
  public void CreateDocumentWhenPassedNull() {
    Mockito.when(mongoTemplate.save(null, "Coin")).thenThrow(new IllegalArgumentException());
    assertFalse(createQueries.CreateCoinDocument(null));
  }

  @Test
  public void CreateDocumentWhenPassedNewCoin() {

    Coin coin = new Coin();
    Mockito.when(mongoTemplate.save(coin, "Coin")).thenReturn(coin);

    assertTrue(createQueries.CreateCoinDocument(coin));
  }

  @Test
  public void CreateDocumentsWhenPassedNull() {
    Mockito.when(mongoTemplate.save(null, "Coin")).thenThrow(new IllegalArgumentException());
    assertFalse(createQueries.CreateCoinDocuments(null));
  }

  @Test
  public void CreateDocumentsWhenPassedNewCoin() {

    Coin coin = new Coin();
    List<Coin> coins = new ArrayList<>();
    coins.add(coin);
    Mockito.when(mongoTemplate.save(coins, "Coin")).thenReturn(coins);

    assertTrue(createQueries.CreateCoinDocuments(coins));
  }
}
