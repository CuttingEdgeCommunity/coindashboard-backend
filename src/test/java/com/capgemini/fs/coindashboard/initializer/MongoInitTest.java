package com.capgemini.fs.coindashboard.initializer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
public class MongoInitTest extends MongoInitTestBaseClass {

  @TestConfiguration
  static class MongoInitContextConfiguration {

    @Bean
    public MongoInit mongoInit() {
      return new MongoInit();
    }
  }

  @Autowired private MongoInit mongoInit;

  @MockBean private CreateQueries createQueries;
  @MockBean private GetQueries getQueries;
  @MockBean private ApiHolder apiHolder;

  @BeforeEach
  public void setUp() {
    Mockito.when(createQueries.CreateCoinDocument(btc_CoinMarketDto)).thenReturn(true);
  }

  @Test
  void afterPropertiesSet_success() {
    Mockito.when(apiHolder.getCoinMarketData("btc")).thenReturn(coinMarketDataResult_correct);
    mongoInit.afterPropertiesSet();
    assertTrue(
        createQueries.CreateCoinDocument(
            apiHolder.getCoinMarketData("btc").getCoinMarketDataDTOS().get(0)));
  }

  @Test
  void afterPropertiesSet_failure(CapturedOutput output) {
    Mockito.when(apiHolder.getCoinMarketData("btc")).thenReturn(null);
    mongoInit.afterPropertiesSet();
    assertTrue(output.getOut().contains("Data not loaded from the APIHolder"));
  }
}
