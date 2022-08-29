package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CoinTranslator {

  // maps symbol to PlaceHolder
  private final Map<String, PlaceHolder> translationMap = new HashMap<>();

  public abstract void initialize(Object data);

  protected PlaceHolder getTranslation(String symbol) {
    return this.translationMap.get(symbol);
  }

  protected List<String> translate(List<String> symbols, TranslationEnum wanted) {
    var result = new ArrayList<String>();
    for (String symbol : symbols) {
      String translation;
      translation =
          switch (wanted) {
            case NAME -> this.getTranslation(symbol).getName();
            case ID -> this.getTranslation(symbol).getId();
          };
      result.add(translation);
    }
    return result;
  }

  public abstract List<String> translate(List<String> symbols, ApiCommunicatorMethodEnum method);
}