package com.capgemini.fs.coindashboard.apiCommunicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ApiHolder { // TODO: placeholder name
  private final Map<ApiProviderEnum, ApiCommunicator> apiCommunicators;

  @Autowired
  public ApiHolder(Set<ApiCommunicator> apiCommunicatorSet){
    this.apiCommunicators = new HashMap<>();
    apiCommunicatorSet.forEach(
        strategy ->this.apiCommunicators.put(strategy.getApiProvider(), strategy));
  }

  public ApiCommunicator getApiCommunicator(ApiProviderEnum apiProviderEnum){
    return this.apiCommunicators.get(apiProviderEnum);
  }

}
