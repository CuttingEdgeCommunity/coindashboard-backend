package com.capgemini.fs.coindashboard.initializer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;

import com.capgemini.fs.coindashboard.CRUDService.queries.CreateQueries;
import com.capgemini.fs.coindashboard.CRUDService.queries.GetQueries;
import com.capgemini.fs.coindashboard.apiCommunicator.ApiHolder;
import com.capgemini.fs.coindashboard.apiCommunicator.dtos.Result;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
@ExtendWith({SpringExtension.class, MockitoExtension.class, OutputCaptureExtension.class})
@Log4j2
public class MongoInitTest {
  @MockBean private CreateQueries createQueries;
  @MockBean private GetQueries getQueries;
  @MockBean private ApiHolder apiHolder;
  @Mock MongoInit mongoInit;

  @Test
  void afterPropertiesSet_success() {
    Optional<Result> testRes1 =
        Optional.of(apiHolder.getTopCoins(250, 0, List.of("usd"))).orElse(null);
    mongoInit.afterPropertiesSet();
    testRes1.ifPresent(result -> createQueries.CreateCoinDocuments(result.getCoins()));
    assertFalse(testRes1.isPresent());
    verify(mongoInit).afterPropertiesSet();
  }

  @Test
  public void afterPropertiesSet_failure(CapturedOutput output) {
    Optional<Result> testRes1 =
        Optional.of(apiHolder.getTopCoins(250, 0, List.of("usd"))).orElse(null);
    mongoInit.afterPropertiesSet();
    assertFalse(output.getOut().contains("Requested 250 coins from API"));
  }

  @Test
  void afterPropertiesSet() {}

  @Test
  void requestingInitialData() {}

  @Test
  void coinInfo() {}

  @Test
  void passingData() {}
}
