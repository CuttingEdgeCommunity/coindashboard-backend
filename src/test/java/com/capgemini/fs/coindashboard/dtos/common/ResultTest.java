package com.capgemini.fs.coindashboard.dtos.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.capgemini.fs.coindashboard.apiCommunicator.ApiProviderEnum;
import org.junit.jupiter.api.Test;

class ResultTest {

  private final Result resultA =
      new Result(ApiProviderEnum.COIN_MARKET_CAP, ResultStatus.SUCCESS, null);
  private final Result resultB =
      new Result(ApiProviderEnum.COIN_MARKET_CAP, ResultStatus.FAILURE, null);
  private final Result resultC = new Result();
  private final Result resultD = new Result();

  @Test
  void testEquals() {
    assertNotEquals(resultA, resultB);
    assertNotEquals(resultA, resultC);
    assertEquals(resultC, resultD);
  }

  @Test
  void testHashCode() {
    assertNotEquals(resultA.hashCode(), resultB.hashCode());
    assertNotEquals(resultA.hashCode(), resultC.hashCode());
    assertEquals(resultC.hashCode(), resultD.hashCode());
  }

  @Test
  void testToString() {
    assertEquals(
        "Result(provider=COIN_MARKET_CAP, status=FAILURE, errorMessage=null)", resultB.toString());
  }

  @Test
  void testSetResultStatus() {
    Result result1 = new Result();
    Result result2 = new Result();
    Result result3 = new Result();
    Result result4 = new Result();

    result1.setErrorMessage("HI, iM AN ERROR");
    result4.setErrorMessage("HI, iM AN ERROR");

    result1.setStatus(false);
    result2.setStatus(true);
    result3.setStatus(false);
    result4.setStatus(true);

    assertEquals(ResultStatus.FAILURE, result1.getStatus());
    assertEquals(ResultStatus.PARTIAL_SUCCESS, result2.getStatus());
    assertEquals(ResultStatus.SUCCESS, result3.getStatus());
    assertEquals(ResultStatus.FAILURE, result4.getStatus());
  }
}
