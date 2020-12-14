package com.repetentia.web.startup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.repetentia.component.property.DatabasePropertySourcePlaceHolderConfigurer;

@Configuration
public class PropertiesPlaceHolderConfig {
    @Bean
    public DatabasePropertySourcePlaceHolderConfigurer databasePropertySourcePlaceHolderConfigurer() {
        return new DatabasePropertySourcePlaceHolderConfigurer();
    }

}
