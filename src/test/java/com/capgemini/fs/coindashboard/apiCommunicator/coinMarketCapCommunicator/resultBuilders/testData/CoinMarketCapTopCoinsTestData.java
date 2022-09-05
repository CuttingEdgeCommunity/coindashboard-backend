package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CoinMarketCapTopCoinsTestData {
  ObjectMapper mapper = new ObjectMapper();
  public JsonNode top3Json;
  public Response top3Response;

  public CoinMarketCapTopCoinsTestData() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinMarketCapCommunicator/resultBuilders/testData/Top3.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    top3Json = mapper.readTree(targetStream);
    top3Response = new Response(200, top3Json);
  }
}
