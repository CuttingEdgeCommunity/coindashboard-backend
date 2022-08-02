package com.capgemini.fs.apiCommunicator.utils;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestBuilderTest {
  private final RequestBuilder rb = new RequestBuilder();
  @Test
  void buildRequestURL() {
    List<String> pathParams = new ArrayList<>() {{
      add("param1");
      add("1");
      add("true");
    }};
    Map<String,String> queryParams = new LinkedHashMap<>(){{
      put("qp1", "1");
      put("qp2", "asd");
      put("qp3", "true");
    }};
    assertEquals("abc.com/param1/1/true?qp1=1&qp2=asd&qp3=true", rb.buildRequestURI("abc.com",pathParams,queryParams));
  }
  @Test
  void buildHttpGetRequest() {
    List<String> headerValues1 = new ArrayList<>(){{
      add("val1");
      add("val2");
    }};
    var req = rb.buildHttpGetRequest("http://hello.com/",
      new LinkedHashMap<>(){{
        put("hd1", headerValues1);
      }});
    assertEquals(headerValues1,req.headers().allValues("hd1"));
  }

  @Test
  void buildURLConnection() throws IOException {
    List<String> headerValues1 = new ArrayList<>(){{
      add("val1");
      add("val2");
    }};
    var req = rb.buildURLConnectionGET("http://hello.com/",
        new LinkedHashMap<>(){{
          put("hd1", headerValues1);
        }});
//    var s = req.getRequestProperties().get("hd1");
    assertEquals(String.join(",",headerValues1),
     req.getRequestProperties().get("hd1").get(0));
  }
}