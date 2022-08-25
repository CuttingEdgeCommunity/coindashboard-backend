package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import java.util.List;

public interface CreateQueries {

  boolean CreateCoinDocument(Coin coin);

  boolean CreateCoinDocuments(List<Coin> coins);
}
