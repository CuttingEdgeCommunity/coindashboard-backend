package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import org.junit.jupiter.api.Test;

class TimeFormatterTest {

  @Test
  void convertStringToTimestamp() throws ParseException {
    assertEquals(
        1659681916000L,
        TimeFormatter.convertStringToTimestamp("08/05/2022 06:45:16", "MM/dd/yyyy hh:mm:ss")
            .getTime());
  }
}
