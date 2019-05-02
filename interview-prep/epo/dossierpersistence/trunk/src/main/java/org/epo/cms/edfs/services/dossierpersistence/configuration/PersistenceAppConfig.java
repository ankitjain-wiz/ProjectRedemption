package org.epo.cms.edfs.services.dossierpersistence.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.epo.cms.edfs.services.dossierpersistence.util.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * @author bkumar
 *
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
public class PersistenceAppConfig {

	@Autowired
	private DatabaseProperties databaseProperties;

	private static final Logger logger = LoggerFactory.getLogger(PersistenceAppConfig.class);
	private static final String SHOW_SQL = "hibernate.show_sql";
	private static final String CHARSET = "hibernate.connection.CharSet";
	private static final String CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
	private static final String USE_UNICODE = "hibernate.connection.useUnicode";
	private static final String UTF_8 = "utf8";
	private static final String DB_DIALECT = "hibernate.dialect";
	
	@Bean
	public DataSource dataSource()  {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(databaseProperties.getDbDriver());
		} catch (PropertyVetoException e) {
			logger.error("Exception while loading datasource" + e);
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
	
	@Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("org.epo");
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
		return properties;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager()
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory().getObject());
		transactionManager.setDataSource(dataSource());
		transactionManager.setDefaultTimeout(databaseProperties.getDbTransactionTimeout());
		return transactionManager;
	}
	
	

}
