package com.repetentia.web.config;

import org.springframework.context.annotation.Bean;

import com.repetentia.component.property.DatabasePropertySourceConfigurer;

public class PropertiesConfig {
//    @Bean
//    public DatabasePropertySourcePlaceHolderConfigurer databasePropertySourcePlaceHolderConfigurer() {
//        return new DatabasePropertySourcePlaceHolderConfigurer();
//    }
    @Bean
    public DatabasePropertySourceConfigurer databasePropertySourcePlaceHolderConfigurer() {
        return new DatabasePropertySourceConfigurer();
    }
}
