package com.capgemini.fs.coindashboard.apiCommunicator.dtos;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiProviderEnum;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Result {
  private ApiProviderEnum provider;
  private ResultStatus status;
  private String errorMessage;
  private List<Coin> coins;
}
