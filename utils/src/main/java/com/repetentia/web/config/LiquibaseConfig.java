package com.repetentia.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.exception.DatabaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LiquibaseConfig {
	@Autowired
	DataSource dataSource;
	@Bean
	public SpringLiquibase liquibase() throws DatabaseException {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:config/liquibase/master.xml");
		liquibase.setDataSource(dataSource);
//		liquibase.setContexts("prod");
		liquibase.setDatabaseChangeLogTable("log_lb_change");
		liquibase.setDatabaseChangeLogLockTable("log_lb_change_lock");
		liquibase.setDefaultSchema("rtadb");
		liquibase.setLiquibaseSchema("rtadb");
		liquibase.setDropFirst(false);
		log.info("# name of database - {} ", liquibase.getDatabaseProductName());
		return liquibase;
	}
}
