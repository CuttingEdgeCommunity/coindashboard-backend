package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class Response {
  private int responseCode;
  private JsonNode responseBody;
}
