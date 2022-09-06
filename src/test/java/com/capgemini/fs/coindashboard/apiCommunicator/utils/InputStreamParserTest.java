package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

class InputStreamParserTest {

  @Test
  void convertStreamToString() {
    String string = "hello";
    String parsed =
        InputStreamParser.convertStreamToString(new ByteArrayInputStream(string.getBytes()));
    assertEquals(string, parsed);
  }
}
