package com.sailpoint.identityiq.config.db;

import com.sailpoint.identityiq.model.entity.SailPointEntity;
import com.sailpoint.identityiq.model.repository.SailPointRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration for all sail point db stuff
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = SailPointRepository.class)
public class SailPointDataBaseConfiguration {

    /**
     * Config for sail point data base
     *
     * @return sail point data base config bean
     */
    @Bean
    public SailPointDataSourceConfig sailPointDataSourceConfig() {
        return new SailPointDataSourceConfig();
    }

    /**
     * Create data source
     *
     * @return data source bean
     */
    @Bean
    public DataSource dataSource() {
        SailPointDataSourceConfig sailPointDataSourceConfig = sailPointDataSourceConfig();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(sailPointDataSourceConfig.getDriveClassName());
        hikariConfig.setJdbcUrl(sailPointDataSourceConfig.getUrl());
        hikariConfig.setUsername(sailPointDataSourceConfig.getUserName());
        hikariConfig.setPassword(sailPointDataSourceConfig.getPassword());
        return new HikariDataSource(hikariConfig);
    }

    /**
     * Create entity manager factory
     *
     * @return entity manager factory bean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());

        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPackagesToScan(SailPointEntity.class.getPackage().getName());
        localContainerEntityManagerFactoryBean.setJpaProperties(buildJpaProperties());

        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Create transaction manager
     *
     * @return jpa transaction manager bean
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    /**
     * Build common all jpa properties
     *
     * @return jpa properties
     */
    private Properties buildJpaProperties() {
        SailPointDataSourceConfig sailPointDataSourceConfig = sailPointDataSourceConfig();

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty(AvailableSettings.HBM2DDL_AUTO, "none");
        jpaProperties.setProperty(AvailableSettings.DIALECT, sailPointDataSourceConfig.getHibernateDialect());
        jpaProperties.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
        jpaProperties.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());

        return jpaProperties;
    }
}
