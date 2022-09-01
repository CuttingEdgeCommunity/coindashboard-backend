package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.PlaceHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
public class CoinGeckoTestBaseClass {
  public JsonNode correctGetNames;
  public Map<String, PlaceHolder> correctTranslationMap = new HashMap<>();
  public Response correctGetNamesR;

  void setupCorrectGetNames() throws JsonProcessingException {
    this.correctTranslationMap.put("zoc", new PlaceHolder("01coin", "01coin", "zoc"));
    this.correctTranslationMap.put(
        "algohalf", new PlaceHolder("0.5X Long Algorand", "0-5x-long-algorand-token", "algohalf"));
    this.correctTranslationMap.put(
        "althalf",
        new PlaceHolder("0.5X Long Altcoin Index", "0-5x-long-altcoin-index-token", "althalf"));
    this.correctTranslationMap.put("btc", new PlaceHolder("Bitcoin", "bitcoin", "btc"));
    this.correctTranslationMap.put("eth", new PlaceHolder("Ethereum", "ethereum", "eth"));
    this.correctGetNames =
        new ObjectMapper()
            .readTree(
                """
                               [
                                               {
                                                   "id": "01coin",
                                                   "symbol": "zoc",
                                                   "name": "01coin"
                                               },
                                               {
                                                   "id": "0-5x-long-algorand-token",
                                                   "symbol": "algohalf",
                                                   "name": "0.5X Long Algorand"
                                               },
                                               {
                                                   "id": "0-5x-long-altcoin-index-token",
                                                   "symbol": "althalf",
                                                   "name": "0.5X Long Altcoin Index"
                                               },
                                               {
                                                    "id": "bitcoin",
                                                    "symbol": "btc",
                                                    "name": "Bitcoin"
                                               },
                                               {
                                                        "id": "ethereum",
                                                        "symbol": "eth",
                                                        "name": "Ethereum"
                                               }
                               ]
                        """);
    this.correctGetNamesR = new Response(200, this.correctGetNames);
    // this.errorR = new Response(400, this.error);
  }
}
