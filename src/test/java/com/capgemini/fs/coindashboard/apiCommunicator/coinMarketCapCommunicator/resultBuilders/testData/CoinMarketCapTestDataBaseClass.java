package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinMarketCapTestDataBaseClass {
  String GOOD_STATUS =
      """
    {"status": {
        "timestamp": "2022-08-31T11:59:14.903Z",
        "error_code": 0,
        "error_message": null,
        "elapsed": 517,
        "credit_count": 5,
        "notice": null,
        "total_count": 9604
    }}""";
  String BAD_STATUS =
      """
      {"status": {
              "timestamp": "2022-09-01T14:12:47.777Z",
              "error_code": 400,
              "error_message": "sample error",
              "elapsed": 0,
              "credit_count": 0
          }}
      """;
  ObjectMapper mapper = new ObjectMapper();
  public JsonNode goodStatusJson = mapper.readTree(GOOD_STATUS);
  public JsonNode badStatusJson = mapper.readTree(BAD_STATUS);
  public Response goodResponse = new Response(200, goodStatusJson);
  public Response badResponse = new Response(400, badStatusJson);

  public CoinMarketCapTestDataBaseClass() throws JsonProcessingException {}
}
