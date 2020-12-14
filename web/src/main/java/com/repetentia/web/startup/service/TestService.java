package com.repetentia.web.startup.service;


import java.util.Collection;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestService {
    @Autowired
    SqlSession sqlSession;
    public void test() {
        Configuration configuration = sqlSession.getConfiguration();
        MapperRegistry mr = configuration.getMapperRegistry();
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        Collection<MappedStatement> stmts = configuration.getMappedStatements();
        log.info("# {} ", stmts);
        for (MappedStatement ms:stmts) {
            Object param = new Object();
            BoundSql boundSql = ms.getBoundSql(param);
            String id= ms.getId();
            String sql = boundSql.getSql();
            log.warn("SQL id : {} - {}", id, sql);
        }
//        DatabaseProperty dp =  new DatabaseProperty();
//        dp.setProfile("test");
//        dp.setProfile("default");
//        sqlSession.getMapper(DatabasePropertyMapper.class).findAll(dp);
    }
}
