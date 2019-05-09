/*package org.epo.cms.edfs.services.casesampling.configuration;

import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.epo.cms.edfs.services.swagger.SwaggerSpringConfig;
import org.epo.cms.edfs.services.swagger.WebAppSpringConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

*//**
 * Configuration class for instantiating spring beans.
 * @author amigarg
 *
 *//*
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.epo.cms.edfs.services",
    excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = {SwaggerSpringConfig.class, WebAppSpringConfig.class})})
@EnableTransactionManagement
@PropertySource("classpath:case-sampling-service.properties")
public class CaseSamplingAppConfig {


  
  @Bean
	 public ResponseValidator responseValidator(){
		 return new ResponseValidator();
	 }
  
  @Bean 
  public PropertyFileReader propertyFileReader(){
		 return new PropertyFileReader();
  }

}
*/