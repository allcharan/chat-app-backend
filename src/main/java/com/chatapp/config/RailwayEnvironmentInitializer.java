package com.chatapp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles Railway's DATABASE_URL format which lacks the "jdbc:" prefix.
 * Automatically adds "jdbc:" to DATABASE_URL if not present.
 */
@Component
public class RailwayEnvironmentInitializer implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String databaseUrl = environment.getProperty("DATABASE_URL");
        
        if (databaseUrl != null && !databaseUrl.startsWith("jdbc:")) {
            // Railway provides URL without jdbc: prefix, so add it
            Map<String, Object> props = new HashMap<>();
            props.put("spring.datasource.url", "jdbc:" + databaseUrl);
            
            MapPropertySource source = new MapPropertySource("railway-datasource-fix", props);
            environment.getPropertySources().addFirst(source);
        }
    }
}
