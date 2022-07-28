package com.capgemini.fs.apiCommunicator.utils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

public class RequestBuilder {
  private final String baseURL;

  public RequestBuilder(String baseUrl) {
    this.baseURL = baseUrl;
  }
  public HttpRequest buildHttpGetRequest(String url, Map<String,List<String>> headers){
    var request = HttpRequest.newBuilder(
            URI.create(url))
        .GET();
    headers.forEach((k,vs)->
      vs.forEach(v->
        request.header(k,v)));
    return request.build();
  }
  public String buildRequestURL(List<String> pathParams, Map<String, String> queryParams){
    StringBuilder sb = new StringBuilder(this.baseURL);
    if (pathParams.size()>0) {
      for (String pathParam :
          pathParams) {
        sb.append(String.format("/%s", pathParam));
      }
    }
    else if (sb.charAt(sb.length()-1) != '/')
      sb.append("/");

    if (queryParams.size()>0) {
      sb.append("?");
      queryParams.forEach((k,v)->sb.append(String.format("%s=%s&", k,v)));
      sb.setLength(sb.length()-1);
    }
    return sb.toString();
  }
}
