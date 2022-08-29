package com.capgemini.fs.coindashboard.apiCommunicator.interfaces;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import java.util.List;
import java.util.Optional;

public interface IApiMethods { // TODO: think about this
  Optional<Result> getTopCoins(int take, int page, List<String> vsCurrencies);

  Optional<Result> getCurrentListing(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline);

  Optional<Result> getHistoricalListing(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo);
}
