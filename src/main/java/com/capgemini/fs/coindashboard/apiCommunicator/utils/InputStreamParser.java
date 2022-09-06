package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InputStreamParser {

  public static String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      sb.setLength(sb.length() - 1);
    } catch (IOException e) {
      log.error(e.getMessage());
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }
    return sb.toString();
  }
}
