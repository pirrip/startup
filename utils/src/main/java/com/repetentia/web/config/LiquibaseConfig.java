package com.repetentia.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.repetentia.component.liquibase.LiquiBaseChangeLogGenerator;

import liquibase.exception.DatabaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LiquibaseConfig {

    @Autowired
    Environment env;
    @Autowired
    DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() throws DatabaseException {
        final String pathMaster = env.getProperty("liquibase.path.master");
        final String schema = env.getProperty("liquibase.schema");
        final String tableChange = env.getProperty("liquibase.table.change");
        final String tableLock = env.getProperty("liquibase.table.lock");

        SpringLiquibase liquibase = new SpringLiquibase();
//		liquibase.setContexts("prod");
        liquibase.setChangeLog(pathMaster);
        liquibase.setDataSource(dataSource);
        liquibase.setDatabaseChangeLogTable(tableChange);
        liquibase.setDatabaseChangeLogLockTable(tableLock);
        liquibase.setDefaultSchema(schema);
        liquibase.setLiquibaseSchema(schema);
        liquibase.setDropFirst(false);
        log.info("# name of database - {} ", liquibase.getDatabaseProductName());
        return liquibase;
    }

    @Bean
    public LiquiBaseChangeLogGenerator liquiBaseChangeLogGenerator() {
        LiquiBaseChangeLogGenerator liquiBaseChangeLogGenerator = new LiquiBaseChangeLogGenerator(env);
        return liquiBaseChangeLogGenerator;
    }
}
