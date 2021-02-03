package com.repetentia.web.config;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;

import liquibase.integration.spring.SpringLiquibase;


public class SpringLiquibaseUtil {
	public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";
	public static final String SPRING_PROFILE_HEROKU = "heroku";
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	
    private SpringLiquibaseUtil() {
    }

    public static SpringLiquibase createSpringLiquibase(DataSource liquibaseDatasource, LiquibaseProperties liquibaseProperties, DataSource dataSource, DataSourceProperties dataSourceProperties) {
        SpringLiquibase liquibase;
        DataSource liquibaseDataSource = getDataSource(liquibaseDatasource, liquibaseProperties, dataSource);
        if (liquibaseDataSource != null) {
            liquibase = new SpringLiquibase();
            liquibase.setDataSource(liquibaseDataSource);
            return liquibase;
        }
        liquibase = new DataSourceClosingSpringLiquibase();
        liquibase.setDataSource(createNewDataSource(liquibaseProperties, dataSourceProperties));
        return liquibase;
    }

    public static AsyncSpringLiquibase createAsyncSpringLiquibase(Environment env, Executor executor, DataSource liquibaseDatasource, LiquibaseProperties liquibaseProperties, DataSource dataSource, DataSourceProperties dataSourceProperties) {
        AsyncSpringLiquibase liquibase = new AsyncSpringLiquibase(executor, env);
        DataSource liquibaseDataSource = getDataSource(liquibaseDatasource, liquibaseProperties, dataSource);
        if (liquibaseDataSource != null) {
            liquibase.setCloseDataSourceOnceMigrated(false);
            liquibase.setDataSource(liquibaseDataSource);
        } else {
            liquibase.setDataSource(createNewDataSource(liquibaseProperties, dataSourceProperties));
        }
        return liquibase;
    }

    private static DataSource getDataSource(DataSource liquibaseDataSource, LiquibaseProperties liquibaseProperties, DataSource dataSource) {
        if (liquibaseDataSource != null) {
            return liquibaseDataSource;
        }
        if (liquibaseProperties.getUrl() == null && liquibaseProperties.getUser() == null) {
            return dataSource;
        }
        return null;
    }

    private static DataSource createNewDataSource(LiquibaseProperties liquibaseProperties, DataSourceProperties dataSourceProperties) {
        String url = getProperty(liquibaseProperties::getUrl, dataSourceProperties::determineUrl);
        String user = getProperty(liquibaseProperties::getUser, dataSourceProperties::determineUsername);
        String password = getProperty(liquibaseProperties::getPassword, dataSourceProperties::determinePassword);
        return DataSourceBuilder.create().url(url).username(user).password(password).build();
    }

    private static String getProperty(Supplier<String> property, Supplier<String> defaultValue) {
        return Optional.of(property).map(Supplier::get).orElseGet(defaultValue);
    }

}
