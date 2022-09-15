package com.capgemini.fs.coindashboard.configuration;

import com.capgemini.fs.coindashboard.encryptionService.AESService;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class SecretsPostProcessor
    implements EnvironmentPostProcessor, ApplicationListener<ApplicationEvent> {

  private static final DeferredLog log = new DeferredLog();
  private AESService aesService;

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {

    // configurationProperties is a main configuration properties source
    PropertySource<?> propertySource =
        environment.getPropertySources().get("configurationProperties");

    if (propertySource.getProperty("secret.key") == null
        || propertySource.getProperty("secret.iv") == null) {
      log.error("Can't find secret key and/or iv");
      return;
    }

    aesService =
        new AESService(
            propertySource.getProperty("secret.key").toString(),
            propertySource.getProperty("secret.iv").toString());

    String secretPropertiesString = propertySource.getProperty("secret.properties").toString();

    List<String> secretProperties = Arrays.stream(secretPropertiesString.split(" ")).toList(),
        propertiesNotFound = new ArrayList<>(),
        propertiesNotDecrypted = new ArrayList<>();

    StringBuilder decryptedPropertiesStringBuilder = new StringBuilder();

    for (String secret : secretProperties) {
      if (propertySource.getProperty(secret) != null) {
        try {
          String decryptedSecret =
              aesService.decrypt(propertySource.getProperty(secret).toString());
          decryptedPropertiesStringBuilder.append(secret + "=" + decryptedSecret + "\n");
        } catch (Exception ex) {
          propertiesNotDecrypted.add(secret);
        }
      } else {
        propertiesNotFound.add(secret);
      }
    }

    if (propertiesNotFound.size() >= 1) {
      logTroubles(propertiesNotFound, "find");
    }
    if (propertiesNotDecrypted.size() >= 1) {
      logTroubles(propertiesNotDecrypted, "decrypt");
    }

    String decryptedPropertiesString = decryptedPropertiesStringBuilder.toString();

    MutablePropertySources propertySourcesHandle = environment.getPropertySources();

    Properties properties = new Properties();
    try {
      properties.load(new StringReader(decryptedPropertiesString));
    } catch (IOException exception) {
      log.warn("Can't fetch decrypted properties into configuration");
    }
    PropertiesPropertySource decryptedSecretsPropertySource =
        new PropertiesPropertySource("decryptedSecretsProperties", properties);
    propertySourcesHandle.addFirst(decryptedSecretsPropertySource);

    if (propertiesNotFound.size() < 1 && propertiesNotDecrypted.size() < 1) {
      log.info("Successfully decrypted all properties");
    }
  }

  private void logTroubles(List<String> problemSource, String actionName) {
    StringBuilder propertiesNotFoundStringBuilder = new StringBuilder();
    String propertiesWord = problemSource.size() == 1 ? "property" : "properties";
    propertiesNotFoundStringBuilder.append(
        "Can't " + actionName + " " + problemSource.size() + " " + propertiesWord + ": [ ");
    for (String propertyNotFound : problemSource) {
      propertiesNotFoundStringBuilder.append(propertyNotFound + " ");
    }
    propertiesNotFoundStringBuilder.append("]");
    log.warn(propertiesNotFoundStringBuilder.toString());
  }

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    log.replayTo(SecretsPostProcessor.class);
  }
}
