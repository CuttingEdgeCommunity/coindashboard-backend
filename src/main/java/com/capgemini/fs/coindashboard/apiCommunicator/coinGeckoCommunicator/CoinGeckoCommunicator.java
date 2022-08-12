package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiCommunicator;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.RequestBuilder;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.capgemini.fs.coindashboard.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoCommunicator implements ApiCommunicator {

  @Autowired ApiClient client;
  ApiProviderEnum apiProvider = ApiProviderEnum.COIN_GECKO;
  @Autowired
  CoinGeckoResponseParser parser;
  @Override
  public ApiProviderEnum getApiProvider() {
    return this.apiProvider;
  }

  public String CoinId(String name){
    if (name.equals("Bitcoin")){
      return "bitcoin";
    } else {
      return name;
    }
  }
  @Override
  public CoinMarketDataResult getCurrentListing(List<String> coins, List<String> vsCurrencies) {
    final int maxcoinsamount = 250;
    CoinMarketDataResult coinMarketDataResult = new CoinMarketDataResult();
    coinMarketDataResult.setProvider(this.apiProvider);
    Response response = null;
    for (String currency : vsCurrencies) {
      try {
        response =
            client.invokeGet(
                RequestBuilder.buildRequestURI(
                    "https://api.coingecko.com/api/v3/coins/markets",
                    new ArrayList<>(),
                    new LinkedHashMap<>() {
                      {
                        put("ids", String.join(",", coins));
                        put("vs_currency", currency);
                        put("price_change_percentage", "1h,24h,7d,30d");
                        put("per_page", maxcoinsamount + "");
                      }
                    }));
      } catch (IOException e) {
        coinMarketDataResult.setStatus(ResultStatus.FAILURE);
        coinMarketDataResult.setErrorMessage(e.getMessage());
      }
      assert response != null;
      ArrayList<CoinMarketDataDto> coinMarketDataDtos = parser.CurrentParser(response, currency);
      coinMarketDataResult.setCoinMarketDataDTOS(coinMarketDataDtos);
      if (coinMarketDataDtos.size() > 0) {
        coinMarketDataResult.setStatus(ResultStatus.SUCCESS);
      } else {
        coinMarketDataResult.setStatus(ResultStatus.FAILURE);
      }
    }
    return coinMarketDataResult;
  }

  @Override
  public CoinMarketDataResult getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestamp) {
    CoinMarketDataResult coinMarketDataResult = new CoinMarketDataResult();
    for (String coin : coins) {
      for (String currency : vsCurrencies) {
        coinMarketDataResult.setProvider(this.apiProvider);
        Response response;
        ArrayList<CoinMarketDataDto> coinMarketDataDtos = new ArrayList<>();
        try {
          response = client.invokeGet(RequestBuilder
              .buildRequestURI(
                  "https://api.coingecko.com/api/v3/coins/" + CoinId(coin) + "/market_chart",
                  new ArrayList<>(), new LinkedHashMap<>() {{
                    put("vs_currency", currency);
                    put("days", "1");
                    //put("interval", "hourly"); use to decrease accuracy of data
                  }}));
          CoinMarketDataDto coinMarketDataDto = parser.HistoricalParser(response, coin, currency);
          coinMarketDataDtos.add(coinMarketDataDto);

        } catch (IOException e) {
          coinMarketDataResult.setStatus(ResultStatus.FAILURE);
          coinMarketDataResult.setErrorMessage(e.getMessage());
        }
        //ArrayList<CoinMarketDataDto> coinMarketDataDtos = new ArrayList<>();
        //CoinGeckoResponseParser parser = new CoinGeckoResponseParser();
        //assert response != null;
        //CoinMarketDataDto coinMarketDataDto = parser.HistoricalParser(response, coin, currency);
        //coinMarketDataDtos.add(coinMarketDataDto);

        if (coinMarketDataDtos.size() > 0) {
          coinMarketDataResult.setStatus(ResultStatus.SUCCESS);
          coinMarketDataResult.setCoinMarketDataDTOS(coinMarketDataDtos);
        } else {
          coinMarketDataResult.setStatus(ResultStatus.FAILURE);
        }
      }
    }
    return coinMarketDataResult;
  }

}
