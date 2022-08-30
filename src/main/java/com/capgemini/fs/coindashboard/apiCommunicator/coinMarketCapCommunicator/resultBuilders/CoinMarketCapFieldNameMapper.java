package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
final class CoinMarketCapFieldNameMapper extends FieldNameMapper {
  public final String ERROR_CODE = "error_code";
  public final String ERROR_MESSAGE = "error_message";
  public final String NOT_FOUND_MESSAGE = "message";
  public final String STATUS = "status";
  public final String DATA = "data";
  public final String NAME = "name";
  public final String SYMBOL = "symbol";
  public final String MARKET_CAP_RANK = "cmc_rank";
  public final String QUOTE = "quote";
  public final String QUOTES = "quotes";
  public final String INSERTED_QUOTE_NAME = "inserted_quote_name";
  public final String MARKET_CAP = "market_cap";
  public final String DAILY_VOLUME = "volume_24h";
  public final String CURRENT_PRICE = "price";
  public final String PRICE = "price";
  public final String LAST_UPDATE_DATE = "last_updated";
  public final String TIMESTAMP = "timestamp";

  public final Map<IntervalEnum, String> DELTA_MAP =
      Map.of(
          IntervalEnum.ONE_HOUR, "percent_change_1h",
          IntervalEnum.ONE_DAY, "percent_change_24h",
          IntervalEnum.SEVEN_DAY, "percent_change_7d");
}
