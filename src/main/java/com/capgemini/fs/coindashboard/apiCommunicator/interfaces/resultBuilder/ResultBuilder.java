package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;

public interface ResultBuilder {
  ApiCommunicatorMethodEnum getMethod();

  FieldNameMapper getMapper();
}
