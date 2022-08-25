package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.CurrentQuote;
import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Quote;

public interface UpdateQueries {

  boolean UpdateCoinCurrentQuote(String coinName, CurrentQuote newQuote, String vs_currency);

  boolean UpdateCoinPriceChart(String coinName);

  boolean UpdateEveryCoinPriceChart();
}
