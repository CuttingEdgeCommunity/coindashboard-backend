package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.resultBuilder;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
class ResultBuilderTest {
  @Mock(answer = Answers.CALLS_REAL_METHODS)
  ResultBuilder resultBuilder;

  @Test
  void calculateNominalDelta() {
    DecimalFormat twoDForm = new DecimalFormat("#.", DecimalFormatSymbols.getInstance(Locale.UK));
    assertEquals(10, Double.valueOf(twoDForm.format(resultBuilder.calculateNominalDelta(110, 10))));
  }
}
