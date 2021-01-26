package com.repetentia.web.config;

import org.springframework.context.annotation.Bean;

import com.repetentia.component.property.DatabasePropertySourcePlaceHolderConfigurer;

public class PropertiesPlaceHolderConfig {
    @Bean
    public DatabasePropertySourcePlaceHolderConfigurer databasePropertySourcePlaceHolderConfigurer() {
        return new DatabasePropertySourcePlaceHolderConfigurer();
    }

}
