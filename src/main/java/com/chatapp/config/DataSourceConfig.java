package com.chatapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    /**
     * Ensures JDBC URL has proper prefix for PostgreSQL driver.
     * Railway provides DATABASE_URL without "jdbc:" prefix,
     * but PostgreSQL driver requires it.
     */
    public String getJdbcUrl() {
        if (datasourceUrl == null) {
            return null;
        }
        
        // If URL doesn't start with "jdbc:", prepend it
        if (!datasourceUrl.startsWith("jdbc:")) {
            return "jdbc:" + datasourceUrl;
        }
        return datasourceUrl;
    }
}
