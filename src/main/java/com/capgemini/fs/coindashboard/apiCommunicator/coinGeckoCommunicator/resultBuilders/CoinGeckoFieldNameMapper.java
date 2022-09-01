package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.IntervalEnum;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.FieldNameMapper;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
final class CoinGeckoFieldNameMapper extends FieldNameMapper {
  public final String INSERTED_QUOTE_NAME = "inserted_quote_name";
  public final String NAME = "name";
  public final String SYMBOL = "symbol";
  public final String MARKET_CAP_RANK = "market_cap_rank";
  public final String ERROR = "error";
  public final String MARKET_CAP = "market_cap";
  public final String DAILY_VOLUME = "total_volume";
  public final String CURRENT_PRICE = "current_price";
  public final String LAST_UPDATE_DATE = "last_updated";

  public final Map<IntervalEnum, String> DELTA_MAP =
      Map.of(
          IntervalEnum.ONE_HOUR, "price_change_percentage_1h_in_currency",
          IntervalEnum.ONE_DAY, "price_change_percentage_24h_in_currency",
          IntervalEnum.SEVEN_DAY, "price_change_percentage_7d_in_currency");
}
