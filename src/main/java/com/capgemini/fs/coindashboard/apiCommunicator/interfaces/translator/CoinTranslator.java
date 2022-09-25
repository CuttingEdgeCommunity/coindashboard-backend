package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class CoinTranslator {

  // maps symbol to PlaceHolder
  private final Map<String, PlaceHolder> translationMap = new TreeMap<>();

  public abstract void initialize(Response response);

  protected PlaceHolder getTranslation(String symbol) {
    if (this.translationMap.get(symbol) == null) log.error("no such coin symbol: {}", symbol);

    return this.translationMap.get(symbol);
  }

  protected void setTranslation(String symbol, PlaceHolder placeHolder) {
    if (!this.translationMap.containsKey(symbol)) {
      this.translationMap.put(symbol, placeHolder);
    }
  }

  public List<String> translate(List<String> symbols, TranslationEnum wanted) {
    var result = new ArrayList<String>();
    for (String symbol : symbols) {
      try {
        String translation;
        translation =
            switch (wanted) {
              case NAME -> this.getTranslation(symbol).getName();
              case ID -> this.getTranslation(symbol).getId();
            };
        result.add(translation);
      } catch (Exception ignored) {
        log.error("Symbol not found: {}", symbol);
      }
    }
    return result;
  }

  public abstract List<String> translate(List<String> symbols, ApiCommunicatorMethodEnum method);
}
