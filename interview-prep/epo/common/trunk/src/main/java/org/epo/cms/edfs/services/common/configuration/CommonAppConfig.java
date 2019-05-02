package org.epo.cms.edfs.services.common.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.jcs.access.CacheAccess;
import org.epo.cms.edfs.services.common.aop.BICMSApiAspect;
import org.epo.cms.edfs.services.common.cache.CacheScheduler;
import org.epo.cms.edfs.services.common.cache.JCSCacheFactoryBean;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.interceptor.AuthorisationInterceptorForDevOnly;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * @author bkumar
 *
 */
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@PropertySource("classpath:CacheConfig.properties")
public class CommonAppConfig implements SchedulingConfigurer {

  private static final JCSCacheFactoryBean jcsManagerFactoryBean =
      new JCSCacheFactoryBean("/CacheConfig.properties");
  
  private static final  String[] EXCLUDED_PATH = {"/**/*.html", "/**/*.htm", "/**/*.css", "/**/*.png", "/**/*.gif",
      "/**/*.jpg", "/**/*.jpeg", "/**/*.ttf", "/**/*.js", "/**/configuration/**", "/**swagger*"
     , "/**/api-docs"};
  private static final String[] INCLUDED_PATH = {"/**"};

  @Bean
  public MappedInterceptor myInterceptor() {
    return new MappedInterceptor(INCLUDED_PATH, EXCLUDED_PATH, authorisationInterceptor());
  }

  /// testdossierhistory/swagger-resources/configuration/ui

  
  
  // BELOW authorisationInterceptor IS COMMENTED FOR TESTING PURPOSE , UNCOMMENT IT FOR PRODUCTION ENVIRONMENT
  /*@Bean
  public AuthorisationInterceptor authorisationInterceptor() {
    return new AuthorisationInterceptor();
  }*/

  
  //BELOW authorisationInterceptor IS Used FOR TESTING PURPOSE , COMMENT IT FOR PRODUCTION ENVIRONMENT
  @Bean
  public AuthorisationInterceptorForDevOnly authorisationInterceptor() {
    return new AuthorisationInterceptorForDevOnly();
  }
  
  
  @Bean
  public BICMSApiAspect validateBICMSAspect() {
    return new BICMSApiAspect();
  }
  
  
  

  @SuppressWarnings("unchecked")
  @Bean
  public CacheAccess<Object, Object> userCache() throws CustomException {
    jcsManagerFactoryBean.setRegion("users");
    return (CacheAccess<Object, Object>) jcsManagerFactoryBean.getObject();
  }

  @SuppressWarnings("unchecked")
  @Bean
  public CacheAccess<Object, Object> permissionCache() throws CustomException {
    jcsManagerFactoryBean.setRegion("permissions");
    return (CacheAccess<Object, Object>) jcsManagerFactoryBean.getObject();
  }

  @Bean
  public CacheScheduler cronScheduler() {
    return new CacheScheduler();
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(commonTaskExecutor());
  }

  @Bean
  public Executor commonTaskExecutor() {
    return Executors.newScheduledThreadPool(10);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer commonPropertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
