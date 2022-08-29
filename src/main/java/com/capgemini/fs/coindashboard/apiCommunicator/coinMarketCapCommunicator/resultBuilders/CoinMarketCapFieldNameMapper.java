package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;
import org.springframework.stereotype.Component;

@Component
final class CoinMarketCapFieldNameMapper extends FieldNameMapper {
  public final String ERROR_CODE = "error_code";
  public final String ERROR_MESSAGE = "error_message";
  public final String STATUS = "status";
  public final String DATA = "data";
  public final String NAME = "name";
  public final String SYMBOL = "symbol";
  public final String MARKET_CAP_RANK = "cmc_rank";
  public final String QUOTE = "quote";
}
