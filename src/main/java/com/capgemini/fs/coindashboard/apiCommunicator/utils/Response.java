package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

  private int responseCode;
  private JsonNode responseBody;
}
