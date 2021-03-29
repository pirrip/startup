package com.repetentia.component.property;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public final class DatabasePropertySourceConfigurer implements EnvironmentAware {
    public static final String LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME = "localProperties";
    public static final String ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME = "environmentProperties";
    private DatabasePropertyLoader databasePropertyLoader;

    @Override
    public void setEnvironment(final Environment env) {
        this.databasePropertyLoader = new DatabasePropertyLoader(env);
        databasePropertyLoader.loadDataBasePropertySource();
    }

    public void reload() {
        databasePropertyLoader.reload();
    }
}
