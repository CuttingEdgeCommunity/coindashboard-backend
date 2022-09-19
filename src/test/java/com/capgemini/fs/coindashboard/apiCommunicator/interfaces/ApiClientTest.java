package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.HttpRequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
class ApiClientTest {
  @MockBean HttpRequestBuilder httpRequestBuilder;
  @Mock private HttpURLConnection goodHttpURLConnection;
  @Mock private HttpURLConnection badHttpURLConnection;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  ApiClient apiClient;

  int ok = 200;
  int error = 400;
  String badResponse = "{\"error\":\"err\"}";
  String goodResponse = "{\"name\":\"gutname\"}";

  @BeforeEach
  void mockRequestBuilder() throws IOException {
    Mockito.when(badHttpURLConnection.getResponseCode()).thenReturn(error);
    Mockito.when(badHttpURLConnection.getErrorStream())
        .thenReturn(new ByteArrayInputStream(badResponse.getBytes()));

    Mockito.when(goodHttpURLConnection.getResponseCode()).thenReturn(ok);
    Mockito.when(goodHttpURLConnection.getInputStream())
        .thenReturn(new ByteArrayInputStream(goodResponse.getBytes()));

    Mockito.when(httpRequestBuilder.buildURLConnectionGET(Mockito.anyString()))
        .thenCallRealMethod();
    Mockito.when(httpRequestBuilder.buildURLConnectionGET(Mockito.eq("good"), Mockito.anyMap()))
        .thenReturn(goodHttpURLConnection);
    Mockito.when(httpRequestBuilder.buildURLConnectionGET(Mockito.eq("bad"), Mockito.anyMap()))
        .thenReturn(badHttpURLConnection);
    this.apiClient.httpRequestBuilder = httpRequestBuilder;
  }

  @Test
  public void invokeGet() throws IOException {
    Response goodResponse = this.apiClient.invokeGet("good");
    Response badResponse = this.apiClient.invokeGet("bad");
    assertEquals(200, goodResponse.getResponseCode());
    assertEquals(400, badResponse.getResponseCode());
    assertEquals("gutname", goodResponse.getResponseBody().get("name").asText());
    assertEquals("err", badResponse.getResponseBody().get("error").asText());
  }

  @Test
  void invokeGetWithHeaders() throws IOException {
    Response goodResponse = this.apiClient.invokeGet("good", new LinkedHashMap<>());
    Response badResponse = this.apiClient.invokeGet("bad", new LinkedHashMap<>());
    assertEquals(200, goodResponse.getResponseCode());
    assertEquals(400, badResponse.getResponseCode());
    assertEquals("gutname", goodResponse.getResponseBody().get("name").asText());
    assertEquals("err", badResponse.getResponseBody().get("error").asText());
  }

  @Test
  void getResponse() throws IOException {
    Response goodResponse = this.apiClient.getResponse(this.goodHttpURLConnection);
    Response badResponse = this.apiClient.getResponse(this.badHttpURLConnection);
    assertEquals(200, goodResponse.getResponseCode());
    assertEquals(400, badResponse.getResponseCode());
    assertEquals("gutname", goodResponse.getResponseBody().get("name").asText());
    assertEquals("err", badResponse.getResponseBody().get("error").asText());
  }

  @Test
  void buildResponse() {
    Response goodResponse = this.apiClient.buildResponse(ok, this.goodResponse);
    Response badResponse = this.apiClient.buildResponse(error, this.badResponse);
    assertEquals(200, goodResponse.getResponseCode());
    assertEquals(400, badResponse.getResponseCode());
    assertEquals("gutname", goodResponse.getResponseBody().get("name").asText());
    assertEquals("err", badResponse.getResponseBody().get("error").asText());
  }

  @Test
  void parseResponse() throws JsonProcessingException {
    JsonNode goodResponse = this.apiClient.parseResponse(this.goodResponse);
    JsonNode badResponse = this.apiClient.parseResponse(this.badResponse);
    assertEquals("gutname", goodResponse.get("name").asText());
    assertEquals("err", badResponse.get("error").asText());
  }
}
