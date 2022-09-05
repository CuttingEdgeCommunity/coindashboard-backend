package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class HttpRequestBuilderTest {
  HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();

  @Test
  void buildRequestURL() {
    List<String> pathParams = List.of("param1", "1", "true");
    Map<String, String> queryParams = new LinkedHashMap<>();
    queryParams.put("qp1", "1");
    queryParams.put("qp2", "asd");
    queryParams.put("qp3", "true");
    assertEquals(
        "abc.com/param1/1/true?qp1=1&qp2=asd&qp3=true",
        httpRequestBuilder.buildRequestURI("abc.com/%s/%s/%s", pathParams, queryParams));
    assertEquals(
        "abc.com?qp1=1&qp2=asd&qp3=true",
        httpRequestBuilder.buildRequestURI("abc.com", queryParams));
    assertEquals(
        "abc.com/param1/1/true",
        httpRequestBuilder.buildRequestURI("abc.com/%s/%s/%s", pathParams));
    assertEquals("abc.com", httpRequestBuilder.buildRequestURI("abc.com"));
  }

  @Test
  void buildURLConnection() throws IOException {
    List<String> headerValues1 = List.of("val1", "val2");

    var req =
        httpRequestBuilder.buildURLConnectionGET("http://hello.com/", Map.of("hd1", headerValues1));
    var req2 = httpRequestBuilder.buildURLConnectionGET("http://hello.com/");
    assertEquals(String.join("%2C", headerValues1), req.getRequestProperties().get("hd1").get(0));
    assertEquals(0, req2.getRequestProperties().size());
  }
}
