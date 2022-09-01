package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Data
@Getter
@AllArgsConstructor
public class PlaceHolder {
  private String name;
  private String id;
  @Id private String symbol;
}
