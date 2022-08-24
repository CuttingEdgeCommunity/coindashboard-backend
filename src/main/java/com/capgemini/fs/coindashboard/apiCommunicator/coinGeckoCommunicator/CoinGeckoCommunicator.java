package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CoinGeckoCommunicator implements ApiCommunicator {
  private final ApiProviderEnum providerEnum = ApiProviderEnum.COIN_GECKO;

  @Autowired private CoinGeckoClient client;
  @Autowired private CoinGeckoResponseParser parser;

  @Override
  public ApiProviderEnum getApiProvider() {
    return this.providerEnum;
  }

  public String CoinId(String name) {
    if (name.equals("Bitcoin")) {
      return "bitcoin";
    } else {
      return name;
    }
  }
  // Calculating how many days of price history should be returned from CoinGecko.
  // 0 will give last day data, any negative or small* number (expect 0) will give max available
  // data
  // *small= timestamp before first available data in Coingecko about coin.
  public String days(long timestamp) {
    if (timestamp == 0) {
      timestamp = System.currentTimeMillis();
    }
    long time_diff = System.currentTimeMillis() - timestamp;
    final long $1day_in_Millis = 86400000L;
    final long $90days_in_Millis = 7776000000L;
    if (time_diff <= $1day_in_Millis) {
      return "1";
    } else if (time_diff <= $90days_in_Millis) {
      return "90";
    } else {
      return "max";
    }
  }

  @Override
  public CoinMarketDataResult getCurrentListing(List<String> coins, List<String> vsCurrencies) {
    final int maxcoinsamount = 250;
    CoinMarketDataResult coinMarketDataResult = new CoinMarketDataResult();
    coinMarketDataResult.setProvider(this.providerEnum);
    ArrayList<CoinMarketDataDto> coinMarketDataDtos = new ArrayList<>();
    Response response;
    for (String currency : vsCurrencies) {
      try {
        // response = this.client.getCoinsMarkets(coins, currency, "market_cap_desc",
        // maxcoinsamount+"", "", "false", "1h,24h,7d,30d");
        response =
            this.client.getCoinsMarkets(
                coins, currency, "", maxcoinsamount + "", "", "", "1h,24h,7d,30d");
        if (response.getResponseCode() == 200) {
          ArrayList<CoinMarketDataDto> coinMarketDataDtos1Curr =
              parser.CurrentParser(response, currency);
          if (coinMarketDataDtos.size() == 0) {
            coinMarketDataDtos = coinMarketDataDtos1Curr;
          } else {
            for (int i = 0; i < response.getResponseBody().size(); i++) {
              coinMarketDataDtos
                  .get(i)
                  .getQuoteMap()
                  .put(currency, coinMarketDataDtos1Curr.get(i).getQuoteMap().get(currency));
            }
          }
        } else {
          coinMarketDataResult.setStatus(ResultStatus.FAILURE);
          coinMarketDataResult.setErrorMessage("");
        }
      } catch (IOException e) {
        coinMarketDataResult.setStatus(ResultStatus.FAILURE);
        coinMarketDataResult.setErrorMessage(e.getMessage());
      }
    }
    coinMarketDataResult.setCoinMarketDataDTOS(coinMarketDataDtos);
    if (coinMarketDataDtos.size() > 0) {
      coinMarketDataResult.setStatus(ResultStatus.SUCCESS);
    } else {
      coinMarketDataResult.setStatus(ResultStatus.FAILURE);
    }
    return coinMarketDataResult;
  }

  @Override
  public CoinMarketDataResult getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestamp) {
    CoinMarketDataResult coinMarketDataResult = new CoinMarketDataResult();
    coinMarketDataResult.setProvider(this.providerEnum);
    ArrayList<CoinMarketDataDto> coinMarketDataDtos = new ArrayList<>();
    Response response;
    for (String coin : coins) {
      for (int c = 0; c < vsCurrencies.size(); c++) {
        try {
          response = this.client.getMarketChart(CoinId(coin), vsCurrencies.get(c), days(timestamp));
          if (response.getResponseCode() == 200) {
            CoinMarketDataDto coinMarketDataDto1Curr =
                parser.HistoricalParser(response, coin, vsCurrencies.get(c));
            if (c == 0) {
              coinMarketDataDtos.add(coinMarketDataDto1Curr);
            } else {
              coinMarketDataDtos
                  .get(coinMarketDataDtos.size() - 1)
                  .getQuoteMap()
                  .put(
                      vsCurrencies.get(c),
                      coinMarketDataDto1Curr.getQuoteMap().get(vsCurrencies.get(c)));
            }
          } else {
            coinMarketDataResult.setStatus(ResultStatus.FAILURE);
            coinMarketDataResult.setErrorMessage("Could not find coin with the given id");
          }
        } catch (IOException e) {
          coinMarketDataResult.setStatus(ResultStatus.FAILURE);
          coinMarketDataResult.setErrorMessage(e.getMessage());
        }
      }
    }
    if (coinMarketDataDtos.size() > 0) {
      coinMarketDataResult.setStatus(ResultStatus.SUCCESS);
      coinMarketDataResult.setCoinMarketDataDTOS(coinMarketDataDtos);
    } else {
      coinMarketDataResult.setStatus(ResultStatus.FAILURE);
    }
    return coinMarketDataResult;
  }
}
