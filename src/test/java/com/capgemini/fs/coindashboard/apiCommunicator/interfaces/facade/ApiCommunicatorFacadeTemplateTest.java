package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.facade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ApiCommunicatorMethodParametersDto;
import com.capgemini.fs.coindashboard.apiCommunicator.interfaces.ApiCommunicatorMethodEnum;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ApiCommunicatorFacadeTemplateTest {
  Map<ApiCommunicatorMethodEnum, Integer> invokedMethods;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  ApiCommunicatorFacadeTemplate apiCommunicatorFacadeTemplate;

  @BeforeEach
  void init() {
    this.invokedMethods =
        Arrays.stream(ApiCommunicatorMethodEnum.values())
            .collect(Collectors.toMap(Function.identity(), method -> 0));
    Mockito.when(
            this.apiCommunicatorFacadeTemplate.getHistoricalListing(
                anyList(), anyList(), anyLong(), anyLong()))
        .thenAnswer(
            input -> {
              invokedMethods.merge(ApiCommunicatorMethodEnum.HISTORICAL_LISTING, 1, Integer::sum);
              return Optional.empty();
            });
    Mockito.when(
            this.apiCommunicatorFacadeTemplate.getCurrentListing(
                anyList(), anyList(), anyBoolean()))
        .thenAnswer(
            input -> {
              invokedMethods.merge(ApiCommunicatorMethodEnum.CURRENT_LISTING, 1, Integer::sum);
              return Optional.empty();
            });
    Mockito.when(
            this.apiCommunicatorFacadeTemplate.getTopCoins(
                anyInt(), anyInt(), anyList(), eq(false)))
        .thenAnswer(
            input -> {
              invokedMethods.merge(ApiCommunicatorMethodEnum.TOP_COINS, 1, Integer::sum);
              return Optional.empty();
            });
    Mockito.when(this.apiCommunicatorFacadeTemplate.getCoinInfo(anyList()))
        .thenAnswer(
            input -> {
              invokedMethods.merge(ApiCommunicatorMethodEnum.COIN_INFO, 1, Integer::sum);
              return Optional.empty();
            });
  }

  @Test
  void executeMethod() {
    var params = new ApiCommunicatorMethodParametersDto(1, 1, List.of(), List.of(), false, 0L, 0L);
    this.apiCommunicatorFacadeTemplate.executeMethod(
        ApiCommunicatorMethodEnum.HISTORICAL_LISTING, params);
    this.apiCommunicatorFacadeTemplate.executeMethod(
        ApiCommunicatorMethodEnum.CURRENT_LISTING, params);
    this.apiCommunicatorFacadeTemplate.executeMethod(ApiCommunicatorMethodEnum.TOP_COINS, params);
    this.apiCommunicatorFacadeTemplate.executeMethod(ApiCommunicatorMethodEnum.COIN_INFO, params);
    assertTrue(this.invokedMethods.values().stream().allMatch(integer -> integer == 1));
  }
}
