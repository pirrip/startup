package com.repetentia.web.config;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;

import com.repetentia.component.table.CommandMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandMapperConfig implements InitializingBean {
    private SqlSession sqlSession;

    public CommandMapperConfig(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration config = sqlSession.getConfiguration();
        config.addMapper(CommandMapper.class);
    }
}
