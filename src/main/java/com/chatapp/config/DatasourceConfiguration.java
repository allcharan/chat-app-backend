package com.chatapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;

/**
 * Custom datasource configuration to handle Railway's DATABASE_URL format.
 * Railway provides DATABASE_URL without "jdbc:" prefix, but PostgreSQL driver requires it.
 * This configuration automatically adds the prefix if missing.
 */
@Configuration
public class DatasourceConfiguration {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        String fixedUrl = datasourceUrl;
        
        // If URL doesn't start with "jdbc:", prepend it
        // This handles Railway's DATABASE_URL format
        if (!fixedUrl.startsWith("jdbc:")) {
            fixedUrl = "jdbc:" + fixedUrl;
        }
        
        return DataSourceBuilder.create()
                .url(fixedUrl)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
