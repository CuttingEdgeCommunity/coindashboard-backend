package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ApiCommunicatorMethodParametersDto;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;

public interface IApiCommunicatorFacade {

  ApiProviderEnum getApiProvider();

  Result executeMethod(ApiCommunicatorMethodEnum method, ApiCommunicatorMethodParametersDto args);
}
