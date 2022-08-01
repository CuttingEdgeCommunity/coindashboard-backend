package com.capgemini.fs.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.apiCommunicator.utils.RequestBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public class CoinGeckoClient {
  private final RequestBuilder requestBuilder = new RequestBuilder();

  private String convertStreamToString(InputStream is) {

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
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

  public String invoke(String uri) throws IOException {
    InputStream response = requestBuilder
      .buildURLConnectionGET(uri)
      .getInputStream();
    return this.convertStreamToString(response);
  }
}
