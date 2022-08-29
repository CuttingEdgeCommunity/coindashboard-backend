package com.capgemini.fs.coindashboard.apiCommunicator.coinMarketCapCommunicator.resultBuilders;

import com.capgemini.fs.coindashboard.CRUDService.model.documentsTemplates.Coin;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
class CoinMarketCapCurrentListingResultBuilder extends CoinMarketCapMarketDataBuilderBaseClass {
  private final ApiCommunicatorMethodEnum method = ApiCommunicatorMethodEnum.CURRENT_LISTING;

  @Override
  public ApiCommunicatorMethodEnum getMethod() {
    return this.method;
  }

  @Override
  protected List<Coin> buildCoinList(JsonNode data) {
    ArrayList<Coin> result = new ArrayList<>();
    ObjectMapper objMapper = new ObjectMapper();
    Map<String, ObjectNode> responseBodyConverted =
        objMapper.convertValue(data, new TypeReference<>() {});
    for (Map.Entry<String, ObjectNode> coin : responseBodyConverted.entrySet()) {
      if (coin.getValue().size() == 0) {
        continue;
      }
      result.add(
          this.buildSingleCoin(coin.getValue().get(this.mapper.NAME).asText(), coin.getValue()));
    }
    return result;
  }

  @Override
  public void setResultStatus() {
    super.setResultStatus();
    if (this.result.getStatus() == ResultStatus.SUCCESS
        && this.result.getCoins() != null
        && ((List<?>) this.requestArgs[0]).size() > this.result.getCoins().size()) {
      this.result.setStatus(ResultStatus.PARTIAL_SUCCESS);
      String differences =
          ((List<String>) this.requestArgs[0])
              .stream()
                  .filter(
                      element ->
                          this.result.getCoins().stream()
                              .noneMatch(c -> Objects.equals(c.getSymbol(), element)))
                  .collect(Collectors.joining(","));
      this.errorMessage = "coins not found: " + differences;
    }
  }
}
