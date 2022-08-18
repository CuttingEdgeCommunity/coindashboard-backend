package com.capgemini.fs.coindashboard.service.mockSprint4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class MockSprint4Test {
  private Path workingDir;

  @BeforeEach
  public void init() {
    this.workingDir =
        Path.of("", "src/main/java/com/capgemini/fs/coindashboard/service/mockSprint4");
  }

  @Test
  void getMockDataReadFileTest() throws IOException, JSONException {
    Path file = this.workingDir.resolve("Coin.json");
    String expected =
        """
            [{
              "name": "Bitcoin",
              "symbol": "btc",
              "genesis_date": 0,
              "is_token": false,
              "quotes": [
                {
                  "vs_currency": "usd",
                  "currentQuote": {
                    "price": 23438,
                    "deltas": [
                      {
                        "interval": "ONE_HOUR",
                        "pct": -0.8495184072796393,
                        "nominal": -200.81609398135348
                      },
                      {
                        "interval": "ONE_DAY",
                        "pct": -1.7963081233196656,
                        "nominal": -428.719827022755
                      },
                      {
                        "interval": "SEVEN_DAY",
                        "pct": 1.0115327891575143,
                        "nominal": 234.70889766379872
                      },
                      {
                        "interval": "THIRTY_DAY",
                        "pct": 12.552752825351243,
                        "nominal": 2613.986893569027
                      }
                    ],
                    "market_cap": {
                      "$numberLong": "448085289046"
                    },
                    "daily_volume": {
                      "$numberLong": "29841818481"
                    }
                  },
                  "chart": [
                    {
                      "price": 23438,
                      "timestamp": {
                        "$numberLong": "1660746317227"
                      }
                    }
                  ]
                }
              ]
            }]""";
    String content = Files.readString(file);
    JSONAssert.assertEquals(expected.trim(),content.trim(),true);
  }
}
