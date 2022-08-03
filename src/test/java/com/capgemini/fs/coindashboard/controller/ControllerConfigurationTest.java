package com.capgemini.fs.coindashboard.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ControllerConfigurationTest {
  @Autowired private CommandLineRunner clr;

  @Test
  public void initDatabase() throws Exception {
    this.clr.run();
  }
}
