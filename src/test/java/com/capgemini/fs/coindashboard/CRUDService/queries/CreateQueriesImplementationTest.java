package com.capgemini.fs.coindashboard.CRUDService.queries;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
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
@ContextConfiguration(
    classes = {CreateQueriesImplementation.class, MongoTemplate.class, ApiHolder.class})
class CreateQueriesImplementationTest {

  @Autowired private CreateQueriesImplementation createQueries;

  @MockBean private MongoTemplate mongoTemplate;

  @Test
  public void CreateDocumentWhenPassedNull() {
    Mockito.when(mongoTemplate.save(null, "Coin")).thenThrow(new IllegalArgumentException());
    assertFalse(createQueries.createCoinDocument(null));
  }

  @Test
  public void CreateDocumentWhenPassedNewCoin() {

    Coin coin = new Coin();
    Mockito.when(mongoTemplate.save(coin, "Coin")).thenReturn(coin);

    assertTrue(createQueries.createCoinDocument(coin));
  }

  @Test
  public void CreateDocumentsWhenPassedNull() {
    // Mockito.when(mongoTemplate.insertAll(null)).thenThrow(new IllegalStateException());
    createQueries.createCoinDocuments(null);
    // assertEquals("Expected list of coins but null has been provided",
    // assertThrows(IllegalStateException.class, ()->{
    //  mongoTemplate.insertAll(null);
    // }, "Expected list of coins but null has been provided").getMessage());
  }

  @Test
  public void CreateDocumentsWhenPassedNewCoins() {
    Coin coin1 = new Coin();
    Coin coin2 = new Coin();
    createQueries.createCoinDocuments(List.of(coin1, coin2));
  }
}
