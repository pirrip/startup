//package com.repetentia.web.startup.config;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.accept.ContentNegotiationManager;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
//
////@Configuration
//public class TestWebConfig {
//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager){
//      ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//      resolver.setContentNegotiationManager(manager);
//      List<ViewResolver> resolvers = new ArrayList<>();
////      ViewResolver aViewResolver = getJsonViewResolver();
////      resolvers.add(aViewResolver);
//      resolver.setViewResolvers(resolvers);
//      return resolver;
//    }
//}
