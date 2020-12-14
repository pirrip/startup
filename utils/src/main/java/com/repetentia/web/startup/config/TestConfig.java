package com.repetentia.web.startup.config;


import java.util.Collection;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.context.annotation.Configuration
public class TestConfig implements InitializingBean {
    @Autowired
    SqlSession SqlSession;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration configuration = SqlSession.getConfiguration();
        MapperRegistry mr = configuration.getMapperRegistry();
        Collection<MappedStatement> stmts = configuration.getMappedStatements();
        log.info("# {} ", stmts);
        for (MappedStatement ms:stmts) {
            Object param = new Object();
            BoundSql boundSql = ms.getBoundSql(param);
            String sql = boundSql.getSql();
            log.warn("SQL - {}", sql);
        }
        
    }
}
