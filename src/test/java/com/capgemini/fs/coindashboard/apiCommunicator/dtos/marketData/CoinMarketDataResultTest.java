package com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;

class CoinMarketDataResultTest {

  private final QuoteDto quote =
      new QuoteDto(
          new ArrayList<>() {
            {
              add(new PriceDto(123d, 123L));
            }
          },
          new ArrayList<>() {
            {
              add(new DeltaDto(IntervalEnum.ONE_HOUR, 123d, 123d));
            }
          },
          1233L);
  private final CoinMarketDataDto coinMarketData =
      new CoinMarketDataDto(
          "1",
          "11",
          new LinkedHashMap<>() {
            {
              put("usd", quote);
            }
          });
  private final CoinMarketDataResult coinMarketDataResultA =
      new CoinMarketDataResult(
          ApiProviderEnum.COIN_MARKET_CAP,
          ResultStatus.SUCCESS,
          "",
          new ArrayList<>() {
            {
              add(coinMarketData);
            }
          });
  private final CoinMarketDataResult coinMarketDataResultB =
      new CoinMarketDataResult(
          ApiProviderEnum.COIN_MARKET_CAP,
          ResultStatus.SUCCESS,
          "",
          new ArrayList<>() {
            {
              add(coinMarketData);
            }
          });
  private final CoinMarketDataResult coinMarketDataResultC =
      new CoinMarketDataResult(
          ApiProviderEnum.COIN_MARKET_CAP,
          ResultStatus.SUCCESS,
          "",
          new ArrayList<>() {
            {
              add(coinMarketData);
              add(coinMarketData);
            }
          });

  @Test
  void testEquals() {
    assertEquals(this.coinMarketDataResultA, this.coinMarketDataResultB);
    assertNotEquals(this.coinMarketDataResultA, this.coinMarketDataResultC);
  }

  @Test
  void testHashCode() {
    assertEquals(this.coinMarketDataResultA.hashCode(), this.coinMarketDataResultB.hashCode());
    assertNotEquals(this.coinMarketDataResultA.hashCode(), this.coinMarketDataResultC.hashCode());
  }

  @Test
  void testToString() {
    assertEquals(
        "CoinMarketDataResult(coinMarketDataDTOS=[CoinMarketDataDto(name=1, symbol=11,"
            + " quoteMap={usd=QuoteDto(prices=[PriceDto(price=123.0, timestamp=123)],"
            + " deltas=[DeltaDto(interval=ONE_HOUR, percentChange=123.0, nominalChange=123.0)],"
            + " lastUpdateTimestampMillis=1233)})])",
        this.coinMarketDataResultA.toString());
  }
}
