package com.capgemini.fs.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.CoinMarketCapCommunicator;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CoinMarketCapCommunicatorTest {

  //
//  @TestConfiguration
//  static class CoinMarketCapCommunicatorContextConfiguration{
//    @Bean
//    public CoinMarketCapCommunicator coinMarketCapCommunicator(){
//      return new CoinMarketCapCommunicator();
//    }
//  }
//
//  @Autowired
//  private CoinMarketCapCommunicator coinMarketCapCommunicator;
//
//  @MockBean
//  private ApiClient apiClient;
//
//
//  @MockBean
//  private CoinMarketCapResponseParser parser;
  private CoinMarketCapCommunicator coinMarketCapCommunicator = new CoinMarketCapCommunicator();
//  @BeforeEach
//  public void setUp() throws IOException {
//    Mockito.when(apiClient.invokeGet(""))
//        .thenReturn(new Response());
//  }

  @Test
  void getCurrentListing() {
    coinMarketCapCommunicator.getCurrentListing(
        new ArrayList<>() {{
          add("bitcoin,ethereum");
        }},
        new ArrayList<>() {{
          add("usd");
        }});
    System.out.println();
  }
}