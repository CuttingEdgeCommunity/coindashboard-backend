package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class FieldNameMapper {
  private Map<String, String> fieldMap;

  @Autowired
  public FieldNameMapper() {
    this.init();
  }

  protected void init() {}

  public String getFieldName(String field) {
    return this.fieldMap.get(field);
  }

  protected void addFieldMapping(String field, String fieldName) {
    this.fieldMap.putIfAbsent(field, fieldName);
  }
}
