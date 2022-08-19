package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ApiClient {
  private static final Logger log = LogManager.getLogger(ApiClient.class);

  public Response invokeGet(String uri) throws IOException {
    var connection = RequestBuilder.buildURLConnectionGET(uri);
    return getResponse(connection);
  }

  public Response invokeGet(String uri, Map<String, List<String>> headers) throws IOException {
    log.info("Connecting to: " + uri);
    var connection = RequestBuilder.buildURLConnectionGET(uri, headers);
    return getResponse(connection);
  }

  private Response getResponse(HttpURLConnection connection) throws IOException {
    int status = connection.getResponseCode();
    log.info("connection: " + connection.getURL() + " return code: " + status);
    String body;
    try {
      body = InputStreamParser.convertStreamToString(connection.getInputStream());
    } catch (Exception e) {
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

  public JsonNode parseResponse(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readTree(json);
  }
}
