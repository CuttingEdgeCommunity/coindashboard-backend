package com.capgemini.fs.coindashboard.apiCommunicator.interfaces.translator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
class CoinTranslatorTest {
  @Mock(answer = Answers.CALLS_REAL_METHODS)
  CoinTranslator coinTranslator;

  @BeforeEach
  void init() throws IllegalAccessException {
    FieldUtils.writeField(
        coinTranslator,
        "translationMap",
        Map.of(
            "symbol1",
            new PlaceHolder("name1", "id1"),
            "symbol2",
            new PlaceHolder("name2", "id2"),
            "symbol3",
            new PlaceHolder("name3", "id3")),
        true);
  }

  @Test
  void getTranslation() {
    assertNull(this.coinTranslator.getTranslation("adsfadsfa"));
    assertEquals(new PlaceHolder("name1", "id1"), this.coinTranslator.getTranslation("symbol1"));
  }

  @Test
  void translate() {
    assertEquals(
        List.of("name1", "name3"),
        this.coinTranslator.translate(List.of("symbol1", "symbol3"), TranslationEnum.NAME));
    assertEquals(
        List.of("id1", "id3"),
        this.coinTranslator.translate(List.of("symbol1", "symbol3"), TranslationEnum.ID));
  }
}
