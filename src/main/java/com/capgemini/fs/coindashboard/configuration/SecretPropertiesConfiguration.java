package com.capgemini.fs.coindashboard.configuration;

import com.capgemini.fs.coindashboard.encryptionService.AESService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.SpringDataMongoDB;

@Configuration
public class SecretPropertiesConfiguration {
  private AESService aesService;
/* chcę wstrzyknąć AESService tutaj i przed zainicjalizowaniem zaszyfrowanej property odszyfrować ją

  /* How to override autoconfiguration in spring boot */
  /* How to override spring-boot-starter-data-mongodb configuration
  *
  * src/main/resources/META-INF/spring.factories
  *
  * org.springframework.boot.autoconfigure.EnableAutoConfiguration
  * io.spring.lab.common.event.DomainEventPublisherAutoConfiguration
  * io.spring.lab.common.facade.FacadeAutoConfiguration
  * io.spring.lab.common.math.MathPropertiesAutoConfiguration
  */
}
