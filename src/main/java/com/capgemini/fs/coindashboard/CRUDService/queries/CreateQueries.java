package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import java.util.List;

public interface CreateQueries {

  boolean createCoinDocument(Coin coin);

  boolean createCoinDocumentWithUpdatingDetails(Coin coin);

  void createCoinDocuments(List<Coin> coins);
}
