package com.capgemini.fs.coindashboard.service.mockSprint4;

import com.capgemini.fs.coindashboard.service.CoinServiceImplementation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MockSprint4 {

  private static final Logger LOG = LogManager.getLogger(CoinServiceImplementation.class);

  public static String getMockData() {
    String mock = null;
    try {
      mock = Files.readString(Paths.get("Mock.json"), StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mock;
  }
}
