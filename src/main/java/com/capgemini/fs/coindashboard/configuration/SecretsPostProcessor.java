package com.capgemini.fs.coindashboard.configuration;

import com.capgemini.fs.coindashboard.encryptionService.AESService;
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
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
@Component
public class SecretsPostProcessor implements EnvironmentPostProcessor,
    ApplicationListener<ApplicationEvent> {

  private static final DeferredLog log = new DeferredLog();
  private AESService aesService;

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {

    /*TEMP-DOC:
    * preprocessor works in a way that it reads a property that tells which
    * properties are secrets. Then it reads these properties and creates a list of them.
    * Each item on the list is decrypted and added to a string builder which will
    * be then used to create a custom property source that is added on top of all
    * property sources, so it overrides all the secret values
    *
    * Properties: secret.key and secrets.iv must be provided at application startup
    * in order to correctly decrypt secrets
    * */

    //TO-DO: add docker iv and key params - ask buddy how to pass them in a secure way

    PropertySource<?> propertySource = environment.getPropertySources()
        .get("configurationProperties");

    aesService = new AESService(propertySource.getProperty("secret.key").toString(),
        propertySource.getProperty("secret.iv").toString());

    String secretPropertiesString = propertySource.getProperty("secret.properties").toString();
    List<String> secretProperties = Arrays.stream(secretPropertiesString.split(" ")).toList(),
      propertiesNotFound = new ArrayList<>(),
      propertiesNotDecrypted = new ArrayList<>();

    StringBuilder decryptedPropertiesStringBuilder = new StringBuilder();

    for (String secret : secretProperties) {
      if(propertySource.getProperty(secret) != null){
        try{
          String decryptedSecret = aesService.decrypt(propertySource.getProperty(secret).toString());
          decryptedPropertiesStringBuilder.append(secret+"="+decryptedSecret+"\n");
        }
        catch (Exception ex)
        {
          propertiesNotDecrypted.add(secret);

        }
      }
      else
      {
        propertiesNotFound.add(secret);
      }
    }

    //TO DO: EXTRACT A GENERIC VERSION OF THESE TWO METHODS - DRY!
    if(propertiesNotFound.size()>=1)
    {
      StringBuilder propertiesNotFoundStringBuilder = new StringBuilder();
      String propertiesWord = propertiesNotFound.size() == 1 ? "property" : "properties";
      propertiesNotFoundStringBuilder.append("SecretsPostProcessor could not find "+
          propertiesNotFound.size()+" "+propertiesWord+": [ ");
      for (String propertyNotFound: propertiesNotFound) {
        propertiesNotFoundStringBuilder.append(propertyNotFound+" ");
      }
      propertiesNotFoundStringBuilder.append("]");
      log.warn(propertiesNotFoundStringBuilder.toString());
    }
    if(propertiesNotDecrypted.size()>=1)
    {
      StringBuilder propertiesNotDecryptedStringBuilder = new StringBuilder();
      String propertiesWord = propertiesNotFound.size() == 1 ? "property" : "properties";
      propertiesNotDecryptedStringBuilder.append("SecretsPostProcessor could not decrypt "+
          propertiesNotDecrypted.size()+" "+propertiesWord+": [ ");
      for (String propertyNotDecrypted : propertiesNotDecrypted) {
        propertiesNotDecryptedStringBuilder.append(propertyNotDecrypted+" ");
      }
      propertiesNotDecryptedStringBuilder.append("]");
      log.warn(propertiesNotDecryptedStringBuilder.toString());
    }

    String decryptedPropertiesString = decryptedPropertiesStringBuilder.toString();

    MutablePropertySources propertySourcesHandle = environment.getPropertySources();

    Properties properties = new Properties();
    try{
      properties.load(new StringReader(decryptedPropertiesString));
    }
    catch(IOException exception) {
        log.warn("SecretsPostProcessor could not initialize new Properties object with values");
    }
    PropertiesPropertySource decryptedSecretsPropertySource = new PropertiesPropertySource("decryptedSecretsProperties", properties);
    propertySourcesHandle.addFirst(decryptedSecretsPropertySource);
  }

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    log.replayTo(SecretsPostProcessor.class);
  }
}
