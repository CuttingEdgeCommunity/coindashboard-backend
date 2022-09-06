package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestBuilder {

  public HttpURLConnection buildURLConnectionGET(String uri, Map<String, List<String>> headers)
      throws IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
    connection.setRequestMethod("GET");
    headers.forEach(
        (k, vs) ->
            connection.setRequestProperty(
                URLEncoder.encode(k, StandardCharsets.UTF_8),
                String.join(URLEncoder.encode(",", StandardCharsets.UTF_8), vs)));
    return connection;
  }

  public HttpURLConnection buildURLConnectionGET(String uri) throws IOException {
    return this.buildURLConnectionGET(uri, new LinkedHashMap<>());
  }

  public String buildRequestURI(String baseURL, List<String> pathParams) {
    return this.buildRequestURI(baseURL, pathParams, new LinkedHashMap<>());
  }

  public String buildRequestURI(String baseURL, Map<String, String> queryParams) {
    return this.buildRequestURI(baseURL, new ArrayList<>(), queryParams);
  }

  public String buildRequestURI(String baseURL) {
    return this.buildRequestURI(baseURL, new ArrayList<>(), new HashMap<>());
  }

  public String buildRequestURI(
      String baseURL, List<String> pathParams, Map<String, String> queryParams) {
    baseURL = String.format(baseURL, pathParams.toArray());
    StringBuilder sb = new StringBuilder(baseURL);

    if (queryParams.size() > 0) {
      sb.append("?");
      queryParams.forEach(
          (k, v) ->
              sb.append(
                  String.format(
                      "%s=%s&",
                      URLEncoder.encode(k, StandardCharsets.UTF_8),
                      URLEncoder.encode(v, StandardCharsets.UTF_8))));

      sb.setLength(sb.length() - 1);
    }
    return sb.toString();
  }
}
