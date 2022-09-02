package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CoinMarketCapHistoricalListingTestData {
  ObjectMapper mapper = new ObjectMapper();
  public JsonNode btcEthJson;
  public Response btcEthResponse;

  public CoinMarketCapHistoricalListingTestData() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinMarketCapCommunicator/resultBuilders/testData/BtcEthHistoricalListing.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    btcEthJson = mapper.readTree(targetStream);
    btcEthResponse = new Response(200, btcEthJson);
  }
}
