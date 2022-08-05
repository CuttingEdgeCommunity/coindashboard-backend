package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

class ApiClientTest {

  @Test
  void parseResponse() throws JsonProcessingException {
    String body = "{\"name\":\"jakub\",\"age\":22}";
    JsonNode parsed = new ApiClient().parseResponse(body);
    assertEquals(parsed.get("name").asText(), "jakub");
    assertEquals(parsed.get("age").asInt(), 22);
  }
}