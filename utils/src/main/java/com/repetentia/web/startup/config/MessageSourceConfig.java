package com.repetentia.web.startup.config;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.repetentia.component.message.DatabaseResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {
    @Autowired
    SqlSession sqlSession;
    
    @Bean
    public DatabaseResourceBundleMessageSource resourceBundleMessageSource() {
        DatabaseResourceBundleMessageSource messageSource = new DatabaseResourceBundleMessageSource();
        messageSource.setSqlSession(sqlSession);
        return messageSource;
    }
}
