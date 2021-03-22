package com.repetentia.web.config;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.repetentia.component.message.DatabaseResourceBundleMessageSource;

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
