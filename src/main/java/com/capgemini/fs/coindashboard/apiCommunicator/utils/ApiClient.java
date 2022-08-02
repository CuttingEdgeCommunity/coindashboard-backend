package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.source.tree.BreakTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ApiClient { // TODO: make this autowired
  private final RequestBuilder requestBuilder = new RequestBuilder();

  private String convertStreamToString(InputStream is) {

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

  public Response invokeGet(String uri) throws IOException {
    var connection = requestBuilder.buildURLConnectionGET(uri);
    int status = connection.getResponseCode();
    String body;
    try {
      body = this.convertStreamToString(connection.getInputStream());
    }catch (FileNotFoundException e){
      body = this.convertStreamToString(connection.getErrorStream());
    }

    return buildResponse(status, body);
  }

  private Response buildResponse(int status, String body){
    Response response = new Response();
    response.setResponseCode(status);
    response.setResponseBody(body);
    return response;
  }

  public JsonNode parseResponse(String json) throws JsonProcessingException { // TODO: Move outside of this class
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readTree(json);
  }
}
