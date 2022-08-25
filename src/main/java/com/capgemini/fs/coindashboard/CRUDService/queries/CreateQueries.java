package com.capgemini.fs.coindashboard.CRUDService.queries;

import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import java.util.List;

public interface CreateQueries {

  boolean CreateCoinDocument(CoinMarketDataDto coinMarketDataDto);

  boolean CreateCoinDocuments(List<CoinMarketDataDto> coinMarketDataDtos);
}
