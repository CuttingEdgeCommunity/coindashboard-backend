package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.marketData.CoinMarketDataResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiHolder { // TODO: placeholder name

  private final Map<ApiProviderEnum, ApiCommunicator> apiCommunicators;

  @Autowired
  public ApiHolder(Set<ApiCommunicator> apiCommunicatorSet) {
    this.apiCommunicators = new HashMap<>();
    apiCommunicatorSet.forEach(
        strategy -> this.apiCommunicators.put(strategy.getApiProvider(), strategy));
  }

  public ApiCommunicator getApiCommunicator(ApiProviderEnum apiProviderEnum) {
    return this.apiCommunicators.get(apiProviderEnum);
  }

  public List<CoinMarketDataResult> getCoinMarketData(
      String coinName) { // TODO: handle crypto names (ie. btc = bitcoin = 1 inCMC)
    List<CoinMarketDataResult> results = new ArrayList<>();
    Iterator<Entry<ApiProviderEnum, ApiCommunicator>> iterator = this.apiCommunicators.entrySet()
        .iterator();
    boolean stop = false;
    while (iterator.hasNext() && !stop) {
      Map.Entry<ApiProviderEnum, ApiCommunicator> entry = iterator.next();
      CoinMarketDataResult result = entry.getValue().getCurrentListing(new ArrayList<>() {{
        add(coinName);
      }}, new ArrayList<>() {{
        add("usd");
      }});
      results.add(result);
      if (result.getStatus() == ResultStatus.SUCCESS) {
        stop = true;
      }
    }
    return results;
  }

  public List<CoinMarketDataResult> getHistoricalCoinMarketData(String coinName, long timestamp) {
    List<CoinMarketDataResult> results = new ArrayList<>();
    Iterator<Entry<ApiProviderEnum, ApiCommunicator>> iterator = this.apiCommunicators.entrySet()
        .iterator();
    boolean stop = false;
    while (iterator.hasNext() && !stop) {
      Map.Entry<ApiProviderEnum, ApiCommunicator> entry = iterator.next();
      CoinMarketDataResult result = entry.getValue().getHistoricalListing(
          new ArrayList<>() {{
            add(coinName);
          }},
          new ArrayList<>() {{
            add("usd");
          }},
          timestamp);
      results.add(result);
      if (result.getStatus() == ResultStatus.SUCCESS) {
        stop = true;
      }
    }
    return results;
  }
}
