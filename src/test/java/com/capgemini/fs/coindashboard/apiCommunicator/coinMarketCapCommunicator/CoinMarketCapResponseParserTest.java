package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
import java.text.DecimalFormat;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

class CoinMarketCapResponseParserTest extends CoinMarketCapTestBaseClass {

  private final CoinMarketCapResponseParser parser = new CoinMarketCapResponseParser();

  @Test
  void parseStatus() {
    assertNull(parser.parseStatus(correctLatest.get("status")));
    assertNull(parser.parseStatus(correctHistorical.get("status")));
    assertEquals(
        "\"symbol\" should only include comma-separated alphanumeric cryptocurrency symbols",
        parser.parseStatus(error.get("status")));
  }

  @Test
  void parseQuoteLatestResult() {
    var resultCorrect = parser.parseQuoteLatestResult(correctLatest);
    var resultError = parser.parseQuoteLatestResult(error);
    assertEquals(ResultStatus.SUCCESS, resultCorrect.getStatus());
    assertEquals(ResultStatus.FAILURE, resultError.getStatus());
    assertNull(resultCorrect.getErrorMessage());
    assertEquals(
        "\"symbol\" should only include comma-separated alphanumeric cryptocurrency symbols",
        resultError.getErrorMessage());
  }

  @Test
  void parseCoinsQuoteLatestResult() throws ParseException {
    var resultCorrect = parser.parseCoinsQuoteLatestResult(correctLatest.get("data"));
    assertEquals(0.4378632733017602,
        resultCorrect.get(0).getQuoteMap().get("USD").getDeltas().get(0).getPercentChange());
  }

  @Test
  void parseQuoteHistoricalResult() {
    var resultCorrect = parser.parseQuoteHistoricalResult(correctHistorical);
    var resultError = parser.parseQuoteHistoricalResult(error);
    assertEquals(ResultStatus.SUCCESS, resultCorrect.getStatus());
    assertEquals(ResultStatus.FAILURE, resultError.getStatus());
    assertNull(resultCorrect.getErrorMessage());
    assertEquals(
        "\"symbol\" should only include comma-separated alphanumeric cryptocurrency symbols",
        resultError.getErrorMessage());
  }

  @Test
  void parseCoinsQuoteHistoricalResult() throws ParseException {
    var resultCorrect = parser.parseCoinsQuoteHistoricalResult(correctHistorical.get("data"));
    assertEquals(0.716910918341749,
        resultCorrect.get(0).getQuoteMap().get("USD").getPrices().get(0).getPrice());
  }

  @Test
  void calculateNominalDelta() {
    DecimalFormat df = new DecimalFormat("###.####");
    assertEquals("2,9126", df.format(parser.calculateNominalDelta(100, 3)));
    assertEquals("-3,0928", df.format(parser.calculateNominalDelta(100, -3)));
    assertEquals("0", df.format(parser.calculateNominalDelta(100, 0)));
  }

}
