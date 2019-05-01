package com.jpa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseProperties {
	
	private static final String DB_USERNAME = "${db.username}";
	private static final String DB_PSWRD = "${db.password}";
	private static final String DB_DRIVER = "${db.driver}";
	private static final String DB_URL = "${db.url}";
	private static final String DB_DIALECT = "${db.dialect}";
	private static final String DB_SHOW_SQL = "${db.show_sql:false}";

	private static final String DB_TRANSACTION_TIMEOUT = "${db.transaction.timeout:60}";

	private static final String DB_POOL_MAX_SIZE = "${db.pool.max_size:15}";
	private static final String DB_POOL_MIN_SIZE = "${db.pool.min_size:3}";
	private static final String DB_POOL_AQUIRE_INCREMENT = "${db.pool.aquire_increment:3}";
	private static final String DB_POOL_IDLE_TEST_PERIOD = "${db.pool.idle_test_period:0}";
	private static final String DB_POOL_MAX_STATEMENTS = "${db.pool.max_statements:0}";
	private static final String DB_POOL_TIMEOUT = "${db.pool.timeout:0}";
	private static final String DB_POOL_TEST_ON_CHECKOUT = "${db.pool.test_on_checkout:true}";
	private static final String DB_POOL_TEST_QUERY = "${db.pool.test_query:SELECT 1 FROM DUAL}";
	
	@Value(DB_USERNAME) 
	private String dbUsername;
	
	@Value(DB_PSWRD) 
	private String dbPassword;
	
	@Value(DB_DRIVER) 
	private String dbDriver;
	
	@Value(DB_URL)
	private String dbUrl;
	
	@Value(DB_DIALECT) 
	private String dbDialect;
	
	@Value(DB_SHOW_SQL)
	private String dbShowSql;
	
	@Value(DB_TRANSACTION_TIMEOUT)
	private int dbTransactionTimeout;

	@Value(DB_POOL_MAX_SIZE)
	private int dbPoolMaxSize;
	
	@Value(DB_POOL_MIN_SIZE) 
	private int dbPoolMinSize;
	
	@Value(DB_POOL_AQUIRE_INCREMENT) 
	private int dbPoolAquireIncrement;
	
	@Value(DB_POOL_IDLE_TEST_PERIOD)
	private int dbPoolIdleTestPeriod;
	
	@Value(DB_POOL_MAX_STATEMENTS)
	private int dbPoolMaxStatements;
	
	@Value(DB_POOL_TIMEOUT) 
	private int dbPoolTimeout;
	
	@Value(DB_POOL_TEST_ON_CHECKOUT) 
	private boolean dbPoolTestOnCheckout;
	
	@Value(DB_POOL_TEST_QUERY)
	private String dbPoolTestQuery;
	
	public String getDbUsername() {
		return dbUsername;
	}
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getDbDriver() {
		return dbDriver;
	}
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public String getDbDialect() {
		return dbDialect;
	}
	public void setDbDialect(String dbDialect) {
		this.dbDialect = dbDialect;
	}
	public String getDbShowSql() {
		return dbShowSql;
	}
	public void setDbShowSql(String dbShowSql) {
		this.dbShowSql = dbShowSql;
	}
	public int getDbTransactionTimeout() {
		return dbTransactionTimeout;
	}
	public void setDbTransactionTimeout(int dbTransactionTimeout) {
		this.dbTransactionTimeout = dbTransactionTimeout;
	}
	public int getDbPoolMaxSize() {
		return dbPoolMaxSize;
	}
	public void setDbPoolMaxSize(int dbPoolMaxSize) {
		this.dbPoolMaxSize = dbPoolMaxSize;
	}
	public int getDbPoolMinSize() {
		return dbPoolMinSize;
	}
	public void setDbPoolMinSize(int dbPoolMinSize) {
		this.dbPoolMinSize = dbPoolMinSize;
	}
	public int getDbPoolAquireIncrement() {
		return dbPoolAquireIncrement;
	}
	public void setDbPoolAquireIncrement(int dbPoolAquireIncrement) {
		this.dbPoolAquireIncrement = dbPoolAquireIncrement;
	}
	public int getDbPoolIdleTestPeriod() {
		return dbPoolIdleTestPeriod;
	}
	public void setDbPoolIdleTestPeriod(int dbPoolIdleTestPeriod) {
		this.dbPoolIdleTestPeriod = dbPoolIdleTestPeriod;
	}
	public int getDbPoolMaxStatements() {
		return dbPoolMaxStatements;
	}
	public void setDbPoolMaxStatements(int dbPoolMaxStatements) {
		this.dbPoolMaxStatements = dbPoolMaxStatements;
	}
	public int getDbPoolTimeout() {
		return dbPoolTimeout;
	}
	public void setDbPoolTimeout(int dbPoolTimeout) {
		this.dbPoolTimeout = dbPoolTimeout;
	}
	public boolean isDbPoolTestOnCheckout() {
		return dbPoolTestOnCheckout;
	}
	public void setDbPoolTestOnCheckout(boolean dbPoolTestOnCheckout) {
		this.dbPoolTestOnCheckout = dbPoolTestOnCheckout;
	}
	public String getDbPoolTestQuery() {
		return dbPoolTestQuery;
	}
	public void setDbPoolTestQuery(String dbPoolTestQuery) {
		this.dbPoolTestQuery = dbPoolTestQuery;
	}
}
