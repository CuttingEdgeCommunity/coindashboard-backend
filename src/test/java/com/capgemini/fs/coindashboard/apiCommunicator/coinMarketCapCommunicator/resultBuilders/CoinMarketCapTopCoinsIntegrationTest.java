package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapFacade;
import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapFieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder.ResultBuilderDirector;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.HttpRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {
      CoinMarketCapFacade.class,
      CoinMarketCapApiClient.class,
      CoinMarketCapFieldNameMapper.class,
      CoinMarketCapTopCoinsResultBuilder.class,
      ResultBuilderDirector.class,
      CoinMarketCapApiClient.class
    })
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
@Disabled
public class CoinMarketCapTopCoinsIntegrationTest {
  @Autowired CoinMarketCapFacade coinMarketCapFacade;

  @MockBean HttpRequestBuilder httpRequestBuilder;

  @BeforeEach
  void init() {
    System.out.println();
  }

  @Test
  void ses() {
    System.out.println();
  }
}
