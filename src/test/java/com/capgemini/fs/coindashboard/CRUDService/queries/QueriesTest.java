package com.capgemini.fs.coindashboard.CRUDService.queries;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QueriesTest {
  @Autowired private UpdateQueries updateQueries;
  PriceDto priceDto = new PriceDto(23, 32);
  List<PriceDto> priceHistory = new ArrayList<PriceDto>(List.of(priceDto));
  QuoteDto quoteDto = new QuoteDto(22, 22, 22, priceHistory, null, 123);

  @Test
  void updateCoinCurrentQuote() {
    assertTrue(updateQueries.UpdateCoinCurrentQuote("bitcoin", quoteDto, "usd"));
  }
}
