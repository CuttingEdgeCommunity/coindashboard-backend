package com.capgemini.fs.coindashboard.apiCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.common.ResultStatus;
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

  public List<Result> getCoinMarketData(String coinName) {
    List<Result> results = new ArrayList<>();
    Iterator<Entry<ApiProviderEnum, ApiCommunicator>> iterator = this.apiCommunicators.entrySet()
        .iterator();
    boolean stop = false;
    while (iterator.hasNext() && !stop) {
      Map.Entry<ApiProviderEnum, ApiCommunicator> entry = iterator.next();
      Result result = entry.getValue().getCurrentListing(new ArrayList<>() {{
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
}
