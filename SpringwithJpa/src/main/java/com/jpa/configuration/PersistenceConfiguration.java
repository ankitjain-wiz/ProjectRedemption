package com.jpa.configuration;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.EJB3NamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

/*@Configuration
@EnableJpaRepositories({"eu.euipo.microservice.payment.persistence.repository"})
@EntityScan("eu.euipo.microservice.payment.persistence.entity")*/
@EnableTransactionManagement
public class PersistenceConfiguration extends HibernateJpaAutoConfiguration {

    /**
     * Spring environment
     */
    @Autowired
    private Environment environment;

    /**
     * Creates the common payment persistence configuration
     *
     * @param dataSource
     * @param jpaProperties
     * @param jtaTransactionManagerProvider
     */
    public PersistenceConfiguration(final DataSource dataSource, final JpaProperties jpaProperties,
                                    final ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
        super(dataSource, jpaProperties, jtaTransactionManagerProvider);
    }


    /**
     * Adds the hibernate properties.
     * <b>NOTE:</b> the properties spring.jpa.hibernate.* from application.properties are not
     * taken into account automatically by auto-configuration because we are using a Hibernate
     * version (Hibernate 4) previous to the one required by the spring-boot version we are using
     * (Hibernate 5). So we use the Spring environment to read them and set them.
     */
    @Override
    protected void customizeVendorProperties(final Map<String, Object> vendorProperties) {
        super.customizeVendorProperties(vendorProperties);

        vendorProperties.put("hibernate.dialect", this.environment.getProperty("spring.jpa.hibernate.dialect"));
        vendorProperties.put("hibernate.generate_statistics", false);
        vendorProperties.put("hibernate.use_sql_comments", false);

        // Allows retrieving Proceeding table info of Proceeding types not mapped in this
        // persistence context
       // vendorProperties.put("hibernate.persister.resolver", CustomPersisterClassResolver.class.getCanonicalName());

        // Spring Boot uses by default the Hibernate Naming Strategy defined by
        // JpaProperties.Naming.DEFAULT_HIBERNATE4_STRATEGY. This implementation replaces camelCase
        // column names by snake_case. In order to avoid this we configure here the desired
        // strategy. This property is valid for Hibernate 4, for Hibernate 5 the property should be
        // spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
        vendorProperties.put("hibernate.ejb.naming_strategy", EJB3NamingStrategy.class.getCanonicalName());
    }
}