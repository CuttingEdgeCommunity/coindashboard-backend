package com.capgemini.fs.coindashboard.apiCommunicator.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
}
