package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;

public interface CreateQueries {

  boolean CreateCoinDocument(Coin coin);
}
