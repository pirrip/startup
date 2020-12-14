//package com.repetentia.web.startup.config;
//
//import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.repetentia.component.session.SessionListenerWithMetrics;
//
//@Configuration
//public class HttpSessionConfig {
//
//    @Bean
//    public ServletListenerRegistrationBean<SessionListenerWithMetrics> sessionListenerWithMetrics() {
//        ServletListenerRegistrationBean<SessionListenerWithMetrics> listenerRegBean = new ServletListenerRegistrationBean<>();
//        listenerRegBean.setListener(new SessionListenerWithMetrics());
//        return listenerRegBean;
//    }
//}
