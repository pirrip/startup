package com.repetentia.web.config;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.repetentia.support.mybatis.BlobFileInputStreamTypeHandler;
import com.repetentia.support.mybatis.BooleanCharTypeHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBatisConfig {
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    private Interceptor[] interceptors;

    private DatabaseIdProvider databaseIdProvider;
    private String typeAliasPackage;
    private String basePackage;
    private String[] mapperLocations;

    public MyBatisConfig(Environment env) {
        this.typeAliasPackage = env.getProperty("mybatis.type-aliases-package");
        this.basePackage = env.getProperty("mybatis.mapper-scan-package");
        this.mapperLocations = env.getProperty("mybatis.mapper-locations", String[].class);
//        this.mapperScanPackage = env.getProperty("mybatis.mapper-scan-package");
        this.databaseIdProvider = databaseIdProvider();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setDatabaseIdProvider(this.databaseIdProvider);
        factory.setTypeAliasesPackage(typeAliasPackage);
        factory.setMapperLocations(resolveMapperLocations());
        factory.setTypeHandlers(registerTypeHandlers());
//        factory.setVfs(SpringBootVFS.class);

        // 필요, pageHelper, 로그 인터셉터, 암호 인터셉터, xss 인터셉터
//        factory.setPlugins(this.interceptors);
        return factory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        log.warn("## SqlSessionTemplate");
        ExecutorType executorType = ExecutorType.REUSE;
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, executorType);
        Configuration configuration = sqlSessionTemplate.getConfiguration();
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        return sqlSessionTemplate;
    }

    public VendorDatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider provider = new VendorDatabaseIdProvider();
        Properties prop = new Properties();
        prop.put("Oracle", "oracle");
        prop.put("MariaDB", "MySql");
        prop.put("MySql", "MySql");
        provider.setProperties(prop);
        return provider;
    }

    @SuppressWarnings("rawtypes")
    public TypeHandler[] registerTypeHandlers() {
        TypeHandler[] typeHandlers = { new BooleanCharTypeHandler(), new BlobFileInputStreamTypeHandler() };
        return typeHandlers;
    };

    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String[0])).flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }
}
