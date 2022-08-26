package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;

public interface IApiCommunicatorFacade {

  ApiProviderEnum getApiProvider();

  Result executeMethod(ApiCommunicatorMethodEnum method, Object... args);
}
