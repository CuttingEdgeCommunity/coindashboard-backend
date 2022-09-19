package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders.testData;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CoinMarketCapCurrentListingTestData {
  ObjectMapper mapper = new ObjectMapper();
  public JsonNode btcEthLeoJson;
  public Response btcEthLeoResponse;

  public CoinMarketCapCurrentListingTestData() throws IOException {
    File initialFile =
        new File(
            "src/test/java/com/capgemini/fs/coindashboard/apiCommunicator/coinMarketCapCommunicator/resultBuilders/testData/BtcEthLeoCurrentListing.txt");
    InputStream targetStream = new FileInputStream(initialFile);
    btcEthLeoJson = mapper.readTree(targetStream);
    btcEthLeoResponse = new Response(200, btcEthLeoJson);
  }
}
