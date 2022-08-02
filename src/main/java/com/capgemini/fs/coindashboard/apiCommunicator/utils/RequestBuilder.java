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

public class RequestBuilder {

  public static HttpURLConnection buildURLConnectionGET(String uri,
      Map<String, List<String>> headers)
      throws IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
    connection.setRequestMethod("GET");
    headers.forEach((k, vs) ->
        connection.setRequestProperty(URLEncoder.encode(k, StandardCharsets.UTF_8),
            String.join(",", vs)));
    return connection;
  }

  public static HttpURLConnection buildURLConnectionGET(String uri)
      throws IOException {
    return RequestBuilder.buildURLConnectionGET(uri, new LinkedHashMap<>());
  }

  public static String buildRequestURI(String baseURL,
      List<String> pathParams) {
    return RequestBuilder.buildRequestURI(baseURL, pathParams, new LinkedHashMap<>());
  }

  public static String buildRequestURI(String baseURL,
      Map<String, String> queryParams) {
    return RequestBuilder.buildRequestURI(baseURL, new ArrayList<>(), queryParams);
  }

  public static String buildRequestURI(String baseURL) {
    return RequestBuilder.buildRequestURI(baseURL, new ArrayList<>(), new HashMap<>());
  }

  public static String buildRequestURI(String baseURL, List<String> pathParams,
      Map<String, String> queryParams) {
    StringBuilder sb = new StringBuilder(baseURL);
    if (pathParams.size() > 0) {
      pathParams.forEach(p -> sb.append(
          String.format("/%s", URLEncoder.encode(p, StandardCharsets.UTF_8)))
      );
    } else if (sb.charAt(sb.length() - 1) != '/') {
      sb.append("/");
    }

    if (queryParams.size() > 0) {
      sb.append("?");
      queryParams.forEach((k, v) -> sb.append(String.format("%s=%s&",
          URLEncoder.encode(k, StandardCharsets.UTF_8),
          URLEncoder.encode(v, StandardCharsets.UTF_8)))

      );
      sb.setLength(sb.length() - 1);
    }
    return sb.toString();
  }
}
