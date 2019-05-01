package com.wahisplus.wcommon.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wahisplus.wcommon.util.DatabaseProperties;

import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * Database bean initialization.
 * 
 * @author soprasteria
 * @version 1.0
 *
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
public class AppConfig {

  @Autowired
  private DatabaseProperties databaseProperties;

  private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
  private static final String SHOW_SQL = "hibernate.show_sql";
  private static final String CHARSET = "hibernate.connection.CharSet";
  private static final String CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
  private static final String USE_UNICODE = "hibernate.connection.useUnicode";
  private static final String UTF_8 = "utf8";
  private static final String DB_DIALECT = "hibernate.dialect";
  private static final String DB_DDL = "hibernate.hbm2ddl.auto";
  private static final String HIBER_META = "hibernate.temp.use_jdbc_metadata_defaults";

  /**
   * Instantiate DataSource bean.
   * 
   * @return Datasource object
   */
  @Bean
  public DataSource dataSource() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(databaseProperties.getDbDriver());
    } catch (PropertyVetoException e) {
      logger.error("Exception while loading datasource {}", e);
    }

    dataSource.setJdbcUrl(databaseProperties.getDbUrl());
    dataSource.setUser(databaseProperties.getDbUsername());
    dataSource.setPassword(databaseProperties.getDbPassword());
    dataSource.setMaxPoolSize(databaseProperties.getDbPoolMaxSize());
    dataSource.setMinPoolSize(databaseProperties.getDbPoolMinSize());
    dataSource.setAcquireIncrement(databaseProperties.getDbPoolAquireIncrement());
    dataSource.setIdleConnectionTestPeriod(databaseProperties.getDbPoolIdleTestPeriod());
    dataSource.setMaxStatements(databaseProperties.getDbPoolMaxStatements());
    dataSource.setCheckoutTimeout(databaseProperties.getDbPoolTimeout());
    dataSource.setTestConnectionOnCheckout(databaseProperties.isDbPoolTestOnCheckout());
    dataSource.setPreferredTestQuery(databaseProperties.getDbPoolTestQuery());
    return dataSource;
  }

  /**
   * Instantiate sessionFactory.
   * 
   * @return LocalSessionFactoryBean as sessionFactory object
   */
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.wahisplus");
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put(DB_DIALECT, databaseProperties.getDbDialect());
    properties.put(SHOW_SQL, databaseProperties.getDbShowSql());
    properties.put(CHARSET, UTF_8);
    properties.put(CHARACTER_ENCODING, UTF_8);
    properties.put(USE_UNICODE, UTF_8);
    properties.put(DB_DDL, "none");
    properties.put(HIBER_META, false);
    return properties;
  }

  /**
   * Instantiate transactionManager.
   * 
   * @return HibernateTransactionManager as transactionManager object
   */
  @Bean
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager(
        sessionFactory().getObject());
    transactionManager.setDataSource(dataSource());
    transactionManager.setDefaultTimeout(databaseProperties.getDbTransactionTimeout());
    return transactionManager;
  }

  /**
   * This is used for locale.
   * @return its return the locale value according to country.
   */
  @Bean
  public LocaleResolver localeResolver() {
    return new SmartLocaleResolver();
  }

  public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {
    List<Locale> locales = Arrays.asList(new Locale("en"),
        new Locale("es-ES"), new Locale("fr-FR"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
      if (StringUtils.isBlank(request.getHeader("Accept-Language"))) {
        return Locale.getDefault();
      }
      List<Locale.LanguageRange> ranges = Locale.LanguageRange.parse("da,es-MX;q=0.8");

      return Locale.lookup(ranges, locales);
    }
  }

}
