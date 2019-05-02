/**
 * (c) Sopra Steria, 2016. All rights reserved.
 */
package org.epo.cms.edfs.services.swagger;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger support for spring-mvc REST services.
 *
 * References: -
 * https://stackoverflow.com/questions/26720090/a-simple-way-to-implement-swagger-in-a-spring-mvc-application
 * - https://springfox.github.io/springfox/docs/current/
 */
@Configuration
@EnableSwagger2
public class SwaggerSpringConfig {

  private static final Logger LOG = LoggerFactory.getLogger(SwaggerSpringConfig.class);

  // rtdoc means runtime-documentation
  private static final String RTDOC_SWAGGER_PROPERTIES_RESOURCE = "/rtdoc-swagger.properties";

  // XXX @Autowired can't autowire something else than system properties. If someone has an idea
  private final Properties swaggerProps = rtdocSwaggerProperties();

  // XXX @Bean(name = "rtdocSwaggerProperties")
  public final Properties rtdocSwaggerProperties() {
    Properties rtdocSwaggerProperties = new Properties();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    LOG.debug(classLoader.getResource(RTDOC_SWAGGER_PROPERTIES_RESOURCE).getPath());

    try {
      rtdocSwaggerProperties
          .load(classLoader.getResourceAsStream(RTDOC_SWAGGER_PROPERTIES_RESOURCE));
    } catch (IOException ex) {
      LOG.error("An exception was thrown", ex);
    }
    return rtdocSwaggerProperties;
  }

  /**
   *
   * @return Docket as value
   */
  @Bean
  public Docket api() {
    LOG.debug(swaggerProps.toString());

    return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.regex(swaggerProps.getProperty("apiPathsRegex", "/api/.*"))).build()
        .apiInfo(apiInfo());
  }


  /**
   *
   */
  private ApiInfo apiInfo() {
    LOG.debug(swaggerProps.toString());

    return new ApiInfo(swaggerProps.getProperty("title", "<no title>"),
        swaggerProps.getProperty("description", "<no description>"),
        swaggerProps.getProperty("version", "<no version>"),
        swaggerProps.getProperty("termsOfServiceUrl", "<no termsOfServiceUrl>"),
        new Contact(swaggerProps.getProperty("contactName", "<no contactName>"),
            swaggerProps.getProperty("contactUrl", "<no contactUrl>"),
            swaggerProps.getProperty("contactEmail", "<no contactEmail>")),
        swaggerProps.getProperty("license", "<no license>"),
        swaggerProps.getProperty("licenseUrl", "<no licenseUrl>"));
  }

}
