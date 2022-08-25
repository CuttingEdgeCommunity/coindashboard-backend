package com.capgemini.fs.coindashboard.initializer;

import static com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum.COIN_GECKO;
import static com.capgemini.fs.coindashboard.dtos.common.ResultStatus.FAILURE;
import static com.capgemini.fs.coindashboard.dtos.common.ResultStatus.SUCCESS;
import static com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum.ONE_DAY;
import static com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum.ONE_HOUR;
import static com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum.SEVEN_DAY;
import static com.capgemini.fs.coindashboard.dtos.marketData.IntervalEnum.THIRTY_DAY;

import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataDto;
import com.capgemini.fs.coindashboard.dtos.marketData.CoinMarketDataResult;
import com.capgemini.fs.coindashboard.dtos.marketData.DeltaDto;
import com.capgemini.fs.coindashboard.dtos.marketData.PriceDto;
import com.capgemini.fs.coindashboard.dtos.marketData.QuoteDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;

public class MongoInitTestBaseClass {

  public final double current_btc_price = 24510d;
  public final double pct_chg1h = 0.31138363660048685;
  public final double pct_chg24h = 0.30500962358209094;
  public final double pct_chg30d = 19.124792427122273;
  public final double pct_chg7d = 6.635564567543452;
  public DeltaDto delta1h, delta24h, delta30d, delta7d;
  public List<DeltaDto> btc_deltas = Arrays.asList(delta1h, delta24h, delta30d, delta7d);
  public List<PriceDto> btc_pricehistory =
      Arrays.asList(new PriceDto(current_btc_price, 1660431261760L));
  public Map<String, QuoteDto> btc_quoteMap =
      Map.of(
          "usd",
          new QuoteDto(
              current_btc_price,
              467532652606d,
              21584082447d,
              this.btc_pricehistory,
              this.btc_deltas,
              0L));
  public CoinMarketDataDto btc_CoinMarketDto = new CoinMarketDataDto("Bitcoin", btc_quoteMap);
  public ArrayList<CoinMarketDataDto> coinMarketDataDtos = new ArrayList<>();
  public CoinMarketDataResult coinMarketDataResult_correct, coinMarketDataResult_failure;

  double nom_chg(double current_price, double pct_change) {
    return current_price * (pct_change / (100 + pct_change));
  }

  @BeforeEach
  void setup() {
    this.delta1h = new DeltaDto(ONE_HOUR, pct_chg1h, nom_chg(current_btc_price, pct_chg1h));
    this.delta24h = new DeltaDto(ONE_DAY, pct_chg24h, nom_chg(current_btc_price, pct_chg24h));
    this.delta7d = new DeltaDto(SEVEN_DAY, pct_chg7d, nom_chg(current_btc_price, pct_chg7d));
    this.delta30d = new DeltaDto(THIRTY_DAY, pct_chg30d, nom_chg(current_btc_price, pct_chg30d));
    this.coinMarketDataDtos.add(btc_CoinMarketDto);
    this.coinMarketDataResult_correct =
        new CoinMarketDataResult(COIN_GECKO, SUCCESS, null, coinMarketDataDtos);
    this.coinMarketDataResult_failure = new CoinMarketDataResult();
    this.coinMarketDataResult_failure.setStatus(FAILURE);
  }
}
