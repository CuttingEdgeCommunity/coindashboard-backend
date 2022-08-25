package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import java.util.List;

public interface IApiCommunicatorFacade {

  ApiProviderEnum getApiProvider();
  Result getTopCoins(Integer take, List<String> vsCurrencies);

  Result getCurrentListing(List<String> coins, List<String> vsCurrencies);

  Result getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo);
}
