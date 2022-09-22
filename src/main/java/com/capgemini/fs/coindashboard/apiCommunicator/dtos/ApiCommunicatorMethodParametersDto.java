package com.capgemini.fs.coindashboard.apiCommunicator.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiCommunicatorMethodParametersDto {
  private int take;
  private int page;
  private List<String> vsCurrencies;
  private List<String> coins;
  private boolean include7dSparkline;
  private Long timestampFrom;
  private Long timestampTo;

  public ApiCommunicatorMethodParametersDto(int take, int page, List<String> vsCurrencies, boolean include7dSparkline) {
    this.take = take;
    this.page = page;
    this.vsCurrencies = vsCurrencies;
    this.include7dSparkline = include7dSparkline;
  }

  public ApiCommunicatorMethodParametersDto(
      List<String> coins, List<String> vsCurrencies, boolean include7dSparkline) {
    this.coins = coins;
    this.vsCurrencies = vsCurrencies;
    this.include7dSparkline = include7dSparkline;
  }

  public ApiCommunicatorMethodParametersDto(
      List<String> coins, List<String> vsCurrencies, Long timestampFrom, Long timestampTo) {
    this.coins = coins;
    this.vsCurrencies = vsCurrencies;
    this.timestampFrom = timestampFrom;
    this.timestampTo = timestampTo;
  }

  public ApiCommunicatorMethodParametersDto(List<String> coins) {
    this.coins = coins;
  }
}
