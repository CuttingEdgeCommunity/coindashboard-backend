package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ApiClient { // TODO: make this autowired

  private final RequestBuilder requestBuilder = new RequestBuilder();

  public Response invokeGet(String uri) throws IOException {
    var connection = requestBuilder.buildURLConnectionGET(uri);
    int status = connection.getResponseCode();
    String body;
    try {
      body = InputStreamParser.convertStreamToString(connection.getInputStream());
    } catch (FileNotFoundException e) {
      body = InputStreamParser.convertStreamToString(connection.getErrorStream());
    }

    return buildResponse(status, body);
  }

  private Response buildResponse(int status, String body) throws JsonProcessingException {
    Response response = new Response();
    response.setResponseCode(status);
    response.setResponseBody(parseResponse(body));
    return response;
  }

  private JsonNode parseResponse(String json)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readTree(json);
  }
}
