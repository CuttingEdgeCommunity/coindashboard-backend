package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator.PlaceHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
public class CoinMarketCapTestBaseClass {
  public JsonNode correctGetNames;
  public Map<String, PlaceHolder> correctTranslationMap = new HashMap<>();
  public List<String> correctNames, correctIds, inputsymbols;
  public Response correctGetNamesR;

  void setupCorrectGetNames() throws JsonProcessingException {
    this.correctTranslationMap.put(
        "6qizk8ybgf6", new PlaceHolder("oi0q3jjo7v", "4554", "6qizk8ybgf6"));
    this.correctTranslationMap.put(
        "gloxcwf04vo", new PlaceHolder("i3vsqatexar", "2956", "gloxcwf04vo"));
    this.correctTranslationMap.put(
        "lqg5gyt9g8g", new PlaceHolder("dsnl073mh5e", "218", "lqg5gyt9g8g"));
    this.inputsymbols = List.of("6qizk8ybgf6", "gloxcwf04vo", "lqg5gyt9g8g");
    this.correctNames =
        List.of(
            this.correctTranslationMap.get("6qizk8ybgf6").getName(),
            this.correctTranslationMap.get("gloxcwf04vo").getName(),
            this.correctTranslationMap.get("lqg5gyt9g8g").getName());
    this.correctIds =
        List.of(
            this.correctTranslationMap.get("6qizk8ybgf6").getId(),
            this.correctTranslationMap.get("gloxcwf04vo").getId(),
            this.correctTranslationMap.get("lqg5gyt9g8g").getId());
    this.correctGetNames =
        new ObjectMapper()
            .readTree(
                """
                                  {
                                           "status": {
                                               "timestamp": "2022-09-01T13:31:42.377Z",
                                               "error_code": 0,
                                               "error_message": null,
                                               "elapsed": 0,
                                               "credit_count": 1,
                                               "notice": null
                                           },
                                           "data": [
                                               {
                                                   "id": 4554,
                                                   "rank": 7727,
                                                   "name": "oi0q3jjo7v",
                                                   "symbol": "6qizk8ybgf6",
                                                   "slug": "jncejxojwrs",
                                                   "is_active": 3155,
                                                   "first_historical_data": "2022-09-01T13:31:42.377Z",
                                                   "last_historical_data": "2022-09-01T13:31:42.377Z",
                                                   "platform": null
                                               },
                                               {
                                                   "id": 2956,
                                                   "rank": 5551,
                                                   "name": "i3vsqatexar",
                                                   "symbol": "gloxcwf04vo",
                                                   "slug": "2gh33uhsdpc",
                                                   "is_active": 1152,
                                                   "first_historical_data": "2022-09-01T13:31:42.377Z",
                                                   "last_historical_data": "2022-09-01T13:31:42.377Z",
                                                   "platform": null
                                               },
                                               {
                                                   "id": 218,
                                                   "rank": 2529,
                                                   "name": "dsnl073mh5e",
                                                   "symbol": "lqg5gyt9g8g",
                                                   "slug": "rtzoabioqq",
                                                   "is_active": 9662,
                                                   "first_historical_data": "2022-09-01T13:31:42.377Z",
                                                   "last_historical_data": "2022-09-01T13:31:42.377Z",
                                                   "platform": null
                                               }
                                           ]
                                  }
                    """);
    this.correctGetNamesR = new Response(200, this.correctGetNames);
    // this.errorR = new Response(400, this.error);
  }
}
