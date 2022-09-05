package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;

public interface IResultBuilder {
  ApiCommunicatorMethodEnum getMethod();

  FieldNameMapper getMapper();

  void reset();

  void setResultProvider();

  void setResultStatus();

  void setErrorMessage();

  void setData(Response response, Object... requestArgs);

  void setCoins();

  Result getResult();
}
