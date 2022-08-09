package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator;

import com.capgemini.fs.coindashboard.apiCommunicator.utils.ApiClient;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

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
                    },
                    {
                        "id": 6209,
                        "name": "apxf7sex6p",
                        "symbol": "BTC",
                        "slug": "rt8g6eytz5j",
                        "is_active": 7282,
                        "is_fiat": null,
                        "circulating_supply": 3966,
                        "total_supply": 2669,
                        "max_supply": 7918,
                        "date_added": "2022-08-05T05:04:13.734Z",
                        "num_market_pairs": 2724,
                        "cmc_rank": 2839,
                        "last_updated": "2022-08-05T05:04:13.734Z",
                        "tags": [
                            "mwgnoekltt",
                            "6sa98ya7p4f",
                            "ah9z1mufie5",
                            "zsvwwletn8o",
                            "cbb6dkyxwzj",
                            "qrozh6elsd",
                            "aqtfy9v78t7",
                            "76vrrlr3t4",
                            "chf7d459pnq",
                            "omgwxjycuno"
                        ],
                        "platform": null,
                        "self_reported_circulating_supply": null,
                        "self_reported_market_cap": null,
                        "quote": {
                            "EUR": {
                                "price": 0.38175804465968466,
                                "volume_24h": 0.5004845032229457,
                                "volume_change_24h": 0.023326503283758848,
                                "percent_change_1h": 0.8272705479452827,
                                "percent_change_24h": 0.2166342592200119,
                                "percent_change_7d": 0.9600905261274526,
                                "percent_change_30d": 0.3650928287751307,
                                "market_cap": 0.42751285704418396,
                                "market_cap_dominance": 9714,
                                "fully_diluted_market_cap": 0.12278944751704901,
                                "last_updated": "2022-08-05T05:04:13.734Z"
                            },
                            "USD": {
                                "price": 0.38175804465968466,
                                "volume_24h": 0.5004845032229457,
                                "volume_change_24h": 0.023326503283758848,
                                "percent_change_1h": 0.8272705479452827,
                                "percent_change_24h": 0.2166342592200119,
                                "percent_change_7d": 0.9600905261274526,
                                "percent_change_30d": 0.3650928287751307,
                                "market_cap": 0.42751285704418396,
                                "market_cap_dominance": 9714,
                                "fully_diluted_market_cap": 0.12278944751704901,
                                "last_updated": "2022-08-05T05:04:13.734Z"
                            }
                        }
                    },
                    {
                        "id": 3382,
                        "name": "eoi1r26mwj",
                        "symbol": "BTC",
                        "slug": "ptkqqnqg8r",
                        "is_active": 4663,
                        "is_fiat": null,
                        "circulating_supply": 5380,
                        "total_supply": 6601,
                        "max_supply": 1756,
                        "date_added": "2022-08-05T05:04:13.734Z",
                        "num_market_pairs": 4453,
                        "cmc_rank": 8250,
                        "last_updated": "2022-08-05T05:04:13.734Z",
                        "tags": [
                            "ngd8ucb1w3",
                            "mawsf674cg",
                            "3op1zq3immn",
                            "aux066hylkt",
                            "eegoow2m51p",
                            "4d7kftm2mjb",
                            "gefj274vawq",
                            "vv4bhgmc55m",
                            "w0onlqtyx9k",
                            "hcjc76gnf5"
                        ],
                        "platform": null,
                        "self_reported_circulating_supply": null,
                        "self_reported_market_cap": null,
                        "quote": {
                            "EUR": {
                                "price": 0.4207545460004636,
                                "volume_24h": 0.05874933296788676,
                                "volume_change_24h": 0.44978870060330434,
                                "percent_change_1h": 0.8357021407569312,
                                "percent_change_24h": 0.5786980627746363,
                                "percent_change_7d": 0.8947774939099324,
                                "percent_change_30d": 0.40272542124684696,
                                "market_cap": 0.7839481088936322,
                                "market_cap_dominance": 2681,
                                "fully_diluted_market_cap": 0.24515053021470434,
                                "last_updated": "2022-08-05T05:04:13.735Z"
                            },
                            "USD": {
                                "price": 0.4207545460004636,
                                "volume_24h": 0.05874933296788676,
                                "volume_change_24h": 0.44978870060330434,
                                "percent_change_1h": 0.8357021407569312,
                                "percent_change_24h": 0.5786980627746363,
                                "percent_change_7d": 0.8947774939099324,
                                "percent_change_30d": 0.40272542124684696,
                                "market_cap": 0.7839481088936322,
                                "market_cap_dominance": 2681,
                                "fully_diluted_market_cap": 0.24515053021470434,
                                "last_updated": "2022-08-05T05:04:13.735Z"
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
                    },
                    {
                        "id": 7867,
                        "name": "gtmp5u5nqx9",
                        "symbol": "ETH",
                        "slug": "t4bgok459sm",
                        "is_active": 8390,
                        "is_fiat": null,
                        "circulating_supply": 4706,
                        "total_supply": 4238,
                        "max_supply": 9972,
                        "date_added": "2022-08-05T05:04:13.735Z",
                        "num_market_pairs": 3980,
                        "cmc_rank": 1831,
                        "last_updated": "2022-08-05T05:04:13.735Z",
                        "tags": [
                            "vwau8j9jfb",
                            "o91a7sg7n1k",
                            "8f8bj6z0xor",
                            "v5boxic03x",
                            "upj8u7a585h",
                            "0zypkijvz9jo",
                            "5vikkio1qci",
                            "hyslwgd9tdj",
                            "xabl60enoub",
                            "88567oxubjb"
                        ],
                        "platform": null,
                        "self_reported_circulating_supply": null,
                        "self_reported_market_cap": null,
                        "quote": {
                            "EUR": {
                                "price": 0.1941304092637104,
                                "volume_24h": 0.764545176390846,
                                "volume_change_24h": 0.6807204914666145,
                                "percent_change_1h": 0.9586356396677813,
                                "percent_change_24h": 0.6288411597797805,
                                "percent_change_7d": 0.5385867191696831,
                                "percent_change_30d": 0.46264223729081055,
                                "market_cap": 0.47413951481237726,
                                "market_cap_dominance": 7812,
                                "fully_diluted_market_cap": 0.5196335559710008,
                                "last_updated": "2022-08-05T05:04:13.735Z"
                            },
                            "USD": {
                                "price": 0.1941304092637104,
                                "volume_24h": 0.764545176390846,
                                "volume_change_24h": 0.6807204914666145,
                                "percent_change_1h": 0.9586356396677813,
                                "percent_change_24h": 0.6288411597797805,
                                "percent_change_7d": 0.5385867191696831,
                                "percent_change_30d": 0.46264223729081055,
                                "market_cap": 0.47413951481237726,
                                "market_cap_dominance": 7812,
                                "fully_diluted_market_cap": 0.5196335559710008,
                                "last_updated": "2022-08-05T05:04:13.735Z"
                            }
                        }
                    },
                    {
                        "id": 7981,
                        "name": "ywat3wgtn69",
                        "symbol": "ETH",
                        "slug": "qaxp8jset3",
                        "is_active": 578,
                        "is_fiat": null,
                        "circulating_supply": 9071,
                        "total_supply": 8355,
                        "max_supply": 5873,
                        "date_added": "2022-08-05T05:04:13.735Z",
                        "num_market_pairs": 2310,
                        "cmc_rank": 300,
                        "last_updated": "2022-08-05T05:04:13.735Z",
                        "tags": [
                            "itn5ob59xwe",
                            "dkp4pyo8ml9",
                            "xlftoiywgq",
                            "huoeep7mrrs",
                            "bf56xjzt4o5",
                            "tqyb8mne8fg",
                            "iyklqlhjxs",
                            "ktmu427zx5h",
                            "w9pm3lwfa6",
                            "5d2l34fzvdk"
                        ],
                        "platform": null,
                        "self_reported_circulating_supply": null,
                        "self_reported_market_cap": null,
                        "quote": {
                            "EUR": {
                                "price": 0.37748344427532476,
                                "volume_24h": 0.7829874227463993,
                                "volume_change_24h": 0.2951376304798412,
                                "percent_change_1h": 0.8665255746657403,
                                "percent_change_24h": 0.46556805142155344,
                                "percent_change_7d": 0.09594281877077626,
                                "percent_change_30d": 0.9011129779094231,
                                "market_cap": 0.30784674777457766,
                                "market_cap_dominance": 897,
                                "fully_diluted_market_cap": 0.7338858711930225,
                                "last_updated": "2022-08-05T05:04:13.735Z"
                            },
                            "USD": {
                                "price": 0.37748344427532476,
                                "volume_24h": 0.7829874227463993,
                                "volume_change_24h": 0.2951376304798412,
                                "percent_change_1h": 0.8665255746657403,
                                "percent_change_24h": 0.46556805142155344,
                                "percent_change_7d": 0.09594281877077626,
                                "percent_change_30d": 0.9011129779094231,
                                "market_cap": 0.30784674777457766,
                                "market_cap_dominance": 897,
                                "fully_diluted_market_cap": 0.7338858711930225,
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
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.7236781671872239,
                                    "volume_24h": 3191,
                                    "market_cap": 0.2172896709746912,
                                    "circulating_supply": 2034,
                                    "total_supply": 8023,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.7236781671872239,
                                    "volume_24h": 3191,
                                    "market_cap": 0.2172896709746912,
                                    "circulating_supply": 2034,
                                    "total_supply": 8023,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.7073290703702153,
                                    "volume_24h": 5271,
                                    "market_cap": 0.11234953218913457,
                                    "circulating_supply": 7199,
                                    "total_supply": 5228,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.7073290703702153,
                                    "volume_24h": 5271,
                                    "market_cap": 0.11234953218913457,
                                    "circulating_supply": 7199,
                                    "total_supply": 5228,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.9057030502092924,
                                    "volume_24h": 5637,
                                    "market_cap": 0.09391885232308095,
                                    "circulating_supply": 9775,
                                    "total_supply": 98,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.9057030502092924,
                                    "volume_24h": 5637,
                                    "market_cap": 0.09391885232308095,
                                    "circulating_supply": 9775,
                                    "total_supply": 98,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.4976884758429796,
                                    "volume_24h": 6526,
                                    "market_cap": 0.1767537543860076,
                                    "circulating_supply": 6895,
                                    "total_supply": 2979,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.4976884758429796,
                                    "volume_24h": 6526,
                                    "market_cap": 0.1767537543860076,
                                    "circulating_supply": 6895,
                                    "total_supply": 2979,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.8416202955773848,
                                    "volume_24h": 2158,
                                    "market_cap": 0.5156852473931561,
                                    "circulating_supply": 3634,
                                    "total_supply": 382,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.8416202955773848,
                                    "volume_24h": 2158,
                                    "market_cap": 0.5156852473931561,
                                    "circulating_supply": 3634,
                                    "total_supply": 382,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.8487444582920673,
                                    "volume_24h": 6460,
                                    "market_cap": 0.8706947130913145,
                                    "circulating_supply": 9190,
                                    "total_supply": 3159,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.8487444582920673,
                                    "volume_24h": 6460,
                                    "market_cap": 0.8706947130913145,
                                    "circulating_supply": 9190,
                                    "total_supply": 3159,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.6530962123881923,
                                    "volume_24h": 6791,
                                    "market_cap": 0.3888507380270525,
                                    "circulating_supply": 6808,
                                    "total_supply": 950,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.6530962123881923,
                                    "volume_24h": 6791,
                                    "market_cap": 0.3888507380270525,
                                    "circulating_supply": 6808,
                                    "total_supply": 950,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.565101283929111,
                                    "volume_24h": 6523,
                                    "market_cap": 0.45042231916458486,
                                    "circulating_supply": 5573,
                                    "total_supply": 4459,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.565101283929111,
                                    "volume_24h": 6523,
                                    "market_cap": 0.45042231916458486,
                                    "circulating_supply": 5573,
                                    "total_supply": 4459,
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
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.41284683031486535,
                                    "volume_24h": 3835,
                                    "market_cap": 0.48313372591364145,
                                    "circulating_supply": 1136,
                                    "total_supply": 9009,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.41284683031486535,
                                    "volume_24h": 3835,
                                    "market_cap": 0.48313372591364145,
                                    "circulating_supply": 1136,
                                    "total_supply": 9009,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.8501625219678648,
                                    "volume_24h": 807,
                                    "market_cap": 0.26371306856961163,
                                    "circulating_supply": 7750,
                                    "total_supply": 7753,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.8501625219678648,
                                    "volume_24h": 807,
                                    "market_cap": 0.26371306856961163,
                                    "circulating_supply": 7750,
                                    "total_supply": 7753,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.8133178726494081,
                                    "volume_24h": 1625,
                                    "market_cap": 0.9850419785565452,
                                    "circulating_supply": 7636,
                                    "total_supply": 4651,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.8133178726494081,
                                    "volume_24h": 1625,
                                    "market_cap": 0.9850419785565452,
                                    "circulating_supply": 7636,
                                    "total_supply": 4651,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.3087924697822857,
                                    "volume_24h": 4343,
                                    "market_cap": 0.5295072382668742,
                                    "circulating_supply": 3217,
                                    "total_supply": 1663,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.3087924697822857,
                                    "volume_24h": 4343,
                                    "market_cap": 0.5295072382668742,
                                    "circulating_supply": 3217,
                                    "total_supply": 1663,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.10728328104463092,
                                    "volume_24h": 4366,
                                    "market_cap": 0.33659912767871325,
                                    "circulating_supply": 4574,
                                    "total_supply": 5695,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.10728328104463092,
                                    "volume_24h": 4366,
                                    "market_cap": 0.33659912767871325,
                                    "circulating_supply": 4574,
                                    "total_supply": 5695,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.1574529169953689,
                                    "volume_24h": 3576,
                                    "market_cap": 0.8081024573727746,
                                    "circulating_supply": 2691,
                                    "total_supply": 795,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.1574529169953689,
                                    "volume_24h": 3576,
                                    "market_cap": 0.8081024573727746,
                                    "circulating_supply": 2691,
                                    "total_supply": 795,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.9000881984920399,
                                    "volume_24h": 388,
                                    "market_cap": 0.9651314134632836,
                                    "circulating_supply": 9285,
                                    "total_supply": 9883,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.9000881984920399,
                                    "volume_24h": 388,
                                    "market_cap": 0.9651314134632836,
                                    "circulating_supply": 9285,
                                    "total_supply": 9883,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                }
                            }
                        },
                        {
                            "timestamp": "2022-08-05T04:34:08.908Z",
                            "quote": {
                                "EUR": {
                                    "price": 0.06442403830828547,
                                    "volume_24h": 7201,
                                    "market_cap": 0.553448217701791,
                                    "circulating_supply": 9659,
                                    "total_supply": 8253,
                                    "timestamp": "2022-08-05T04:34:08.908Z"
                                },
                                "USD": {
                                    "price": 0.06442403830828547,
                                    "volume_24h": 7201,
                                    "market_cap": 0.553448217701791,
                                    "circulating_supply": 9659,
                                    "total_supply": 8253,
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
