package com.repetentia.web.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

public class TilesConfig {
    @Autowired
    private Environment env;

    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        viewResolver.setOrder(0);
        return viewResolver;
    }

//    public ErrorViewResolver errorViewResolver() {
//        return null;
//    }
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");

        resolver.setExceptionMappings(mappings); // None by default
        resolver.setDefaultErrorView("error"); // No default
        resolver.setExceptionAttribute("ex"); // Default is "exception"
        resolver.setWarnLogCategory("example.MvcLogger"); // No default
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        String tilesSettings = env.getProperty("tiles.settings");
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[] { tilesSettings });
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }
}
