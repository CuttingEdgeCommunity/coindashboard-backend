package com.capgemini.fs.coindashboard.service.mockSprint4;


import com.capgemini.fs.coindashboard.service.CoinServiceImplementation;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
