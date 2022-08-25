package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator;

import com.sun.source.tree.BreakTree;
import org.springframework.stereotype.Component;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CoinTranslator {
  // maps symbol to PlaceHolder
  private Map<String, PlaceHolder> translationMap;
  public abstract void initialize(Object data);
  public PlaceHolder getTranslation(String symbol){
    return this.translationMap.get(symbol);
  }
  public List<String> translate(Set<String> symbols, TranslationEnum wanted){
    var result = new ArrayList<String>();
    for (String symbol :
        symbols) {
      String translation;
      translation = switch(wanted){
        case NAME -> this.getTranslation(symbol).getName();
        case ID -> this.getTranslation(symbol).getId();
      };
      result.add(translation);
    }
    return result;
  }
}
