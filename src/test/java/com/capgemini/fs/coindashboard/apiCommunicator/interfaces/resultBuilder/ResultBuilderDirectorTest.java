package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.ResultStatus;
import com.capgemini.fs.coindashboard.apiCommunicator.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
class ResultBuilderDirectorTest {
  ResultBuilderDirector resultBuilderDirector = new ResultBuilderDirector();

  IResultBuilder resultBuilderGood;
  IResultBuilder resultBuilderBad;

  int methodCallCounter;

  @BeforeEach
  void init() {
    this.resultBuilderGood =
        Mockito.mock(
            IResultBuilder.class,
            invocation -> {
              methodCallCounter++;
              return null;
            });
    this.methodCallCounter = 0;
    this.resultBuilderBad =
        Mockito.mock(
            IResultBuilder.class,
            invocation -> {
              methodCallCounter++;
              return null;
            });
    this.methodCallCounter = 0;
  }

  @Test
  void constructCoinMarketDataResultGoodStatus() {
    Mockito.when(this.resultBuilderGood.getResult())
        .thenAnswer(
            invocationOnMock -> {
              var res = new Result();
              res.setStatus(ResultStatus.SUCCESS);
              return res;
            });
    this.resultBuilderDirector.constructCoinMarketDataResult(
        this.resultBuilderGood, new Response());
    assertEquals(8, this.methodCallCounter);
  }

  @Test
  void constructCoinMarketDataResultBadStatus() {
    Mockito.when(this.resultBuilderBad.getResult())
        .thenAnswer(
            invocationOnMock -> {
              var res = new Result();
              res.setStatus(ResultStatus.FAILURE);
              return res;
            });
    this.resultBuilderDirector.constructCoinMarketDataResult(this.resultBuilderBad, new Response());
    assertEquals(6, this.methodCallCounter);
  }
}
