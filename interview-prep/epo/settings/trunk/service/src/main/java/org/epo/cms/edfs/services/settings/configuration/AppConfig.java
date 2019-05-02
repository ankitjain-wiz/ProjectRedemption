package org.epo.cms.edfs.services.settings.configuration;

import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Defining beans using annotations.
 *
 * @author amigarg
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan
@EnableTransactionManagement
@PropertySource("classpath:settings.properties")
public class AppConfig {

  @Bean
  public PropertyFileReader propertyFileReader() {
    return new PropertyFileReader();
  }

  @Bean
  public ResponseValidator responseValidator() {
    return new ResponseValidator();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
