package com.capgemini.fs.coindashboard.apiCommunicator.coinGeckoCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
public class CoinGeckoTestBaseClass {

  public JsonNode correctLatest, correctHistoricalBTC, correctHistoricalETH, error, errorHistorical;
  public Response correctLatestR,
      btcCorrectHistoricalR,
      ethCorrectHistoricalR,
      errorR,
      errorHistoricalR,
      eurCorrectLatestR;
  public List<String> coins, coinserr, vsCurr;
  public String order = "", per_page = "250", page = "", sparkline = "";
  public String deltas = "1h,24h,7d,30d";
  public final Long timestamp = 0L;

  @BeforeEach
  void setup() throws JsonProcessingException {
    this.coins =
        new ArrayList<>() {
          {
            add("bitcoin");
            add("ethereum");
          }
        };
    this.coinserr =
        new ArrayList<>() {
          {
            add("eth");
            add(" btc");
          }
        };
    this.vsCurr =
        new ArrayList<>() {
          {
            add("usd");
            add("eur");
          }
        };
    this.correctLatest =
        new ObjectMapper()
            .readTree(
                """
                [
                {
                "id":"bitcoin",
                "symbol":"btc",
                "name":"Bitcoin",
                "image":"https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                "current_price":24536,
                "market_cap":467532652606,
                "market_cap_rank":1,
                "fully_diluted_valuation":513480609111,
                "total_volume":21579081552,
                "high_24h":24883,
                "low_24h":24384,
                "price_change_24h":76.08,
                "price_change_percentage_24h":0.31105,
                "market_cap_change_24h":518680431,
                "market_cap_change_percentage_24h":0.11106,
                "circulating_supply":19120850.0,
                "total_supply":21000000.0,
                "max_supply":21000000.0,
                "ath":69045,
                "ath_change_percentage":-64.46409,
                "ath_date":"2021-11-10T14:24:11.849Z",
                "atl":67.81,
                "atl_change_percentage":36083.5299,
                "atl_date":"2013-07-06T00:00:00.000Z",
                "roi":null,
                "last_updated":"2022-08-14T00:59:41.641Z",
                "price_change_percentage_1h_in_currency":0.393973378804501,
                "price_change_percentage_24h_in_currency":0.311054946340371,
                "price_change_percentage_30d_in_currency":19.25093503698925,
                "price_change_percentage_7d_in_currency":6.748482190694132
                },
                {
                "id":"ethereum",
                "symbol":"eth",
                "name":"Ethereum",
                "image":"https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
                "current_price":1991.93,"market_cap":239422060578,
                "market_cap_rank":2,
                "fully_diluted_valuation":null,
                "total_volume":14323862111,
                "high_24h":2017.71,
                "low_24h":1962.2,
                "price_change_24h":29.34,
                "price_change_percentage_24h":1.49486,
                "market_cap_change_24h":4575302601,
                "market_cap_change_percentage_24h":1.94821,
                "circulating_supply":120086072.383993,
                "total_supply":120085427.189872,
                "max_supply":null,
                "ath":4878.26,
                "ath_change_percentage":-59.1671,
                "ath_date":"2021-11-10T14:24:19.604Z",
                "atl":0.432979,
                "atl_change_percentage":459953.63563,
                "atl_date":"2015-10-20T00:00:00.000Z",
                "roi":{
                      "times":107.57854593250748,
                      "currency":"btc",
                      "percentage":10757.85459325075
                      },
                "last_updated":"2022-08-14T01:00:12.682Z",
                "price_change_percentage_1h_in_currency":0.46341956833608927,
                "price_change_percentage_24h_in_currency":1.494863752011452,
                "price_change_percentage_30d_in_currency":67.23047748302015,
                "price_change_percentage_7d_in_currency":17.636437464699064
                }
                ]
            """);

    this.correctHistoricalBTC =
        new ObjectMapper()
            .readTree(
                """
                    {
                            "prices": [
                                [
                                    1660581057860,
                                    24227.50368656992
                                ],
                                [
                                    1660581324495,
                                    24238.317370760105
                                ],
                                [
                                    1660581609008,
                                    24223.082774986317
                                ]
                            ]
                        }
                """);
    this.correctHistoricalETH =
        new ObjectMapper()
            .readTree(
                """
                    {
                            "prices": [
                                [
                                    1660581057860,
                                    24227.50368656992
                                ],
                                [
                                    1660581324495,
                                    24238.317370760105
                                ],
                                [
                                    1660581609008,
                                    24223.082774986317
                                ]
                            ]
                        }
                """);
    this.error = new ObjectMapper().readTree("""
                    {}
                """);
    this.errorHistorical =
        new ObjectMapper()
            .readTree(
                """
                [
                {"error":"Could not find coin with the given id"}
                ]""");

    // this.correctLatestR = new Response(200, this.correctLatest);
    this.correctLatestR = new Response(200, this.correctLatest);
    this.eurCorrectLatestR =
        new Response(
            200,
            new ObjectMapper()
                .readTree(
                    """
        [
        {
        "id": "bitcoin",
        "symbol": "btc",
        "name": "Bitcoin",
        "image": "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
        "current_price": 23743,
        "market_cap": 454425634565,
        "market_cap_rank": 1,
        "fully_diluted_valuation": 499027892083,
        "total_volume": 26265626807,
        "high_24h": 23871,
        "low_24h": 23472,
        "price_change_24h": 56.21,
        "price_change_percentage_24h": 0.2373,
        "market_cap_change_24h": 2633721972,
        "market_cap_change_percentage_24h": 0.58295,
        "circulating_supply": 19123056,
        "total_supply": 21000000,
        "max_supply": 21000000,
        "ath": 59717,
        "ath_change_percentage": -60.2068,
        "ath_date": "2021-11-10T14:24:11.849Z",
        "atl": 51.3,
        "atl_change_percentage": 46223.53632,
        "atl_date": "2013-07-05T00:00:00.000Z",
        "roi": null,
        "last_updated": "2022-08-16T12:30:47.089Z",
        "price_change_percentage_1h_in_currency": -0.17336691781953756,
        "price_change_percentage_24h_in_currency": 0.2372961045085263,
        "price_change_percentage_30d_in_currency": 12.93327058009745,
        "price_change_percentage_7d_in_currency": 1.6241398689361093
        },
    {
      "id": "ethereum",
        "symbol": "eth",
        "name": "Ethereum",
        "image": "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
        "current_price": 1883.85,
        "market_cap": 226767083004,
        "market_cap_rank": 2,
        "fully_diluted_valuation": null,
        "total_volume": 14910785211,
        "high_24h": 1895.27,
        "low_24h": 1834.9,
        "price_change_24h": 18.08,
        "price_change_percentage_24h": 0.96911,
        "market_cap_change_24h": 3463235778,
        "market_cap_change_percentage_24h": 1.55091,
        "circulating_supply": 120120596.627605,
        "total_supply": 120118363.877605,
        "max_supply": null,
        "ath": 4228.93,
        "ath_change_percentage": -55.3592,
        "ath_date": "2021-12-01T08:38:24.623Z",
        "atl": 0.381455,
        "atl_change_percentage": 494801.45609,
        "atl_date": "2015-10-20T00:00:00.000Z",
        "roi": {
          "times": 105.05683483496117,
          "currency": "btc",
          "percentage": 10505.683483496116
           },
        "last_updated": "2022-08-16T12:30:40.596Z",
        "price_change_percentage_1h_in_currency": 0.16227397921463863,
        "price_change_percentage_24h_in_currency": 0.9691123214009522,
        "price_change_percentage_30d_in_currency": 40.145975126844554,
        "price_change_percentage_7d_in_currency": 8.181822786553358
    }
    ]
        """));
    this.btcCorrectHistoricalR = new Response(200, this.correctHistoricalBTC);
    this.ethCorrectHistoricalR = new Response(200, this.correctHistoricalETH);
    this.errorR = new Response(400, this.error);
    this.errorHistoricalR = new Response(400, this.errorHistorical);
  }
}
