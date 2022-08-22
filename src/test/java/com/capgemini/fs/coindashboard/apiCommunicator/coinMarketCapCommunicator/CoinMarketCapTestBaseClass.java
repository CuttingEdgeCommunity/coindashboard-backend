package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
public class CoinMarketCapTestBaseClass {

  public JsonNode correctLatest, correctHistorical, error;
  public Response correctLatestR, correctHistoricalR, errorR;
  public List<String> coins, coinserr, vsCurr;
  public final Long timestamp = 1659601993L;

  @BeforeEach
  void setup() throws JsonProcessingException {
    this.coins =
        new ArrayList<>() {
          {
            add("btc");
            add("eth");
          }
        };
    this.coinserr =
        new ArrayList<>() {
          {
            add("btc");
            add(" eth");
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
        new ApiClient()
            .parseResponse(
                """
      {
          "status": {
              "timestamp": "2022-08-05T05:04:13.735Z",
              "error_code": 0,
              "error_message": null,
              "elapsed": 1,
              "credit_count": 2,
              "notice": null
          },
          "data": {
              "BTC": [
                  {
                      "id": 915,
                      "name": "xux6l0ru03",
                      "symbol": "BTC",
                      "slug": "gxz1seh3xd",
                      "is_active": 9396,
                      "is_fiat": null,
                      "circulating_supply": 5928,
                      "total_supply": 4532,
                      "max_supply": 723,
                      "date_added": "2022-08-05T05:04:13.734Z",
                      "num_market_pairs": 1318,
                      "cmc_rank": 5301,
                      "last_updated": "2022-08-05T05:04:13.734Z",
                      "tags": [
                          "oaxgpud2kz8",
                          "phkar3plw",
                          "d2gry3vnfge",
                          "kdzozo75hgm",
                          "mzhz2dttf6",
                          "5d4r4g38z7",
                          "6v5bekd2zan",
                          "9fo1khu5snw",
                          "jtvp3jjvw6l",
                          "p3n0klw7vno"
                      ],
                      "platform": null,
                      "self_reported_circulating_supply": null,
                      "self_reported_market_cap": null,
                      "quote": {
                          "EUR": {
                              "price": 0.3847972592307525,
                              "volume_24h": 0.09616789612331678,
                              "volume_change_24h": 0.9038227755722696,
                              "percent_change_1h": 0.4378632733017602,
                              "percent_change_24h": 0.5252797683057568,
                              "percent_change_7d": 0.8796581138709574,
                              "percent_change_30d": 0.45463360248521445,
                              "market_cap": 0.6537436479855845,
                              "market_cap_dominance": 6065,
                              "fully_diluted_market_cap": 0.667649577818437,
                              "last_updated": "2022-08-05T05:04:13.734Z"
                          },
                          "USD": {
                              "price": 0.3847972592307525,
                              "volume_24h": 0.09616789612331678,
                              "volume_change_24h": 0.9038227755722696,
                              "percent_change_1h": 0.4378632733017602,
                              "percent_change_24h": 0.5252797683057568,
                              "percent_change_7d": 0.8796581138709574,
                              "percent_change_30d": 0.45463360248521445,
                              "market_cap": 0.6537436479855845,
                              "market_cap_dominance": 6065,
                              "fully_diluted_market_cap": 0.667649577818437,
                              "last_updated": "2022-08-05T05:04:13.734Z"
                          }
                      }
                  }
              ],
              "ETH": [
                  {
                      "id": 472,
                      "name": "ndt42kb0rw",
                      "symbol": "ETH",
                      "slug": "wrpn3qxo9jr",
                      "is_active": 4169,
                      "is_fiat": null,
                      "circulating_supply": 8555,
                      "total_supply": 3990,
                      "max_supply": 1982,
                      "date_added": "2022-08-05T05:04:13.735Z",
                      "num_market_pairs": 566,
                      "cmc_rank": 8275,
                      "last_updated": "2022-08-05T05:04:13.735Z",
                      "tags": [
                          "rrflip1ydjd",
                          "dus96fq2vbs",
                          "6n62i8c162j",
                          "4nmowxph1ud",
                          "umv827cqod",
                          "lht9dnh8fed",
                          "i79grlqlrs",
                          "1mh3ostmmoh",
                          "ma361twykw",
                          "mrlxab75xas"
                      ],
                      "platform": null,
                      "self_reported_circulating_supply": null,
                      "self_reported_market_cap": null,
                      "quote": {
                          "EUR": {
                              "price": 0.7723491164846645,
                              "volume_24h": 0.09648972606917616,
                              "volume_change_24h": 0.5104190556478101,
                              "percent_change_1h": 0.4629243521979354,
                              "percent_change_24h": 0.17337785302311803,
                              "percent_change_7d": 0.43695309256927906,
                              "percent_change_30d": 0.8671547911429736,
                              "market_cap": 0.24550527938258004,
                              "market_cap_dominance": 9985,
                              "fully_diluted_market_cap": 0.7796593936798335,
                              "last_updated": "2022-08-05T05:04:13.735Z"
                          },
                          "USD": {
                              "price": 0.7723491164846645,
                              "volume_24h": 0.09648972606917616,
                              "volume_change_24h": 0.5104190556478101,
                              "percent_change_1h": 0.4629243521979354,
                              "percent_change_24h": 0.17337785302311803,
                              "percent_change_7d": 0.43695309256927906,
                              "percent_change_30d": 0.8671547911429736,
                              "market_cap": 0.24550527938258004,
                              "market_cap_dominance": 9985,
                              "fully_diluted_market_cap": 0.7796593936798335,
                              "last_updated": "2022-08-05T05:04:13.735Z"
                          }
                      }
                  }
              ]
          }
      }""");
    this.correctHistorical =
        new ApiClient()
            .parseResponse(
                """
        {
            "status": {
                "timestamp": "2022-08-05T04:34:08.908Z",
                "error_code": 0,
                "error_message": null,
                "elapsed": 1,
                "credit_count": 2,
                "notice": null
            },
            "data": {
                "BTC": {
                    "id": 7276,
                    "name": "2gsn1by2nuy",
                    "symbol": "BTC",
                    "is_active": 8153,
                    "is_fiat": null,
                    "quotes": [
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.716910918341749,
                                    "volume_24h": 8217,
                                    "market_cap": 0.8127545964818206,
                                    "circulating_supply": 1798,
                                    "total_supply": 7137,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.716910918341749,
                                    "volume_24h": 8217,
                                    "market_cap": 0.8127545964818206,
                                    "circulating_supply": 1798,
                                    "total_supply": 7137,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.8852041336573697,
                                    "volume_24h": 5505,
                                    "market_cap": 0.14413801327737574,
                                    "circulating_supply": 4169,
                                    "total_supply": 6466,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.8852041336573697,
                                    "volume_24h": 5505,
                                    "market_cap": 0.14413801327737574,
                                    "circulating_supply": 4169,
                                    "total_supply": 6466,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        }
                    ]
                },
                "ETH": {
                    "id": 1540,
                    "name": "b9a4ey1wb1l",
                    "symbol": "ETH",
                    "is_active": 5001,
                    "is_fiat": null,
                    "quotes": [
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.2558505127048438,
                                    "volume_24h": 3284,
                                    "market_cap": 0.31376675015097755,
                                    "circulating_supply": 8721,
                                    "total_supply": 9116,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.2558505127048438,
                                    "volume_24h": 3284,
                                    "market_cap": 0.31376675015097755,
                                    "circulating_supply": 8721,
                                    "total_supply": 9116,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.041637210497798005,
                                    "volume_24h": 1472,
                                    "market_cap": 0.769883741659368,
                                    "circulating_supply": 3318,
                                    "total_supply": 173,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.041637210497798005,
                                    "volume_24h": 1472,
                                    "market_cap": 0.769883741659368,
                                    "circulating_supply": 3318,
                                    "total_supply": 173,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        }
                    ]
                }
            }
        }""");
    this.error =
        new ApiClient()
            .parseResponse(
                """
        {
            "status": {
                "timestamp": "2022-08-05T04:53:20.395Z",
                "error_code": 400,
                "error_message": "\\"symbol\\" should only include comma-separated alphanumeric cryptocurrency symbols",
                "elapsed": 0,
                "credit_count": 0
            }
        }""");
    this.correctLatestR = new Response(200, this.correctLatest);
    this.correctHistoricalR = new Response(200, this.correctHistorical);
    this.errorR = new Response(400, this.error);
  }
}
