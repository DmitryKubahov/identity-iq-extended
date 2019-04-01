package com.sailpoint.identityiq.config.db;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config for sail point data base source
 */
@Data
public class SailPointDataSourceConfig {

    /**
     * Sail point data source drive class name
     */
    @Value("${dataSource.driverClassName}")
    private String driveClassName;
    /**
     * Sail point data source url
     */
    @Value("${dataSource.url}")
    private String url;
    /**
     * Sail point data source user
     */
    @Value("${dataSource.username}")
    private String userName;
    /**
     * Sail point data source user password
     */
    @Value("${dataSource.password}")
    private String password;
    /**
     * Sail point data hibernate dialect
     */
    @Value("${dataSource.hibernate.dialect}")
    private String hibernateDialect;

}
