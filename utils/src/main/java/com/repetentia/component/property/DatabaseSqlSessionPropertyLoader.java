package com.repetentia.component.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import com.repetentia.support.log.Marker;
import com.repetentia.support.sql.SqlProviderUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseSqlSessionPropertyLoader {
    private final static String RTA_PROPERTY_SOURCE = "RTAPropertySource";
    private final static String DEFAULT = "default";
    private final ConfigurableEnvironment env;
    private final SqlSession sqlSession;

    public DatabaseSqlSessionPropertyLoader(SqlSession sqlSession, org.springframework.core.env.Environment env) {
        this.sqlSession = sqlSession;
        this.env = (ConfigurableEnvironment) env;

        Configuration configuration = sqlSession.getConfiguration();
        configuration.addMapper(DatabasePropertyMapper.class);

        String tablename = env.getProperty("system.property.table");
        if (StringUtils.hasLength(tablename)) {
            SqlProviderUtils.setTableName(tablename);
        }
    }

    public DatabaseProperty conditions(String site, String profile) {
        DatabaseProperty conditions = new DatabaseProperty();
        conditions.setSite(site);
        conditions.setProfile(profile);
        return conditions;
    }

    public List<String> activeProfiles() {
        String[] profiles = env.getActiveProfiles();
        log.info("# Active Profiles - {}", Arrays.toString(profiles));
        List<String> list = new ArrayList<String>();
        list.add(DEFAULT);
        for (String profile : profiles) {
            list.add(profile);
        }
        if (list.size() == 1) {
            String[] defaultProfiles = env.getDefaultProfiles();
            for (String profile : defaultProfiles) {
                list.add(profile);
            }
        }

        return list;
    }

    public void loadDataBasePropertySource() {
        try {
            Map<String, Object> sourceMap = dbTableListToSourceMap();
            MutablePropertySources mps = this.env.getPropertySources();
            PropertySource<Map<String, Object>> propertySource = new DatabasePropertySource(RTA_PROPERTY_SOURCE, sourceMap);
            mps.addFirst(propertySource);
        } catch (RuntimeException e) {
            log.warn(Marker.BOOT_CONFIG, "# NO JDNI DATASOURCE - DISABLING DATABASE PROPERTIES !!!");
        }
    }

    public void reload() {
        Map<String, Object> sourceMap = dbTableListToSourceMap();
        MutablePropertySources mps = this.env.getPropertySources();
        PropertySource<?> propertySource = mps.get(RTA_PROPERTY_SOURCE);
        @SuppressWarnings("unchecked")
        Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
        Set<Entry<String, Object>> entrySet = sourceMap.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            source.put(key, value);
        }
    }

    public List<String> activeSite() {
        List<String> list = new ArrayList<String>();
        list.add(DEFAULT);
        String site = env.getProperty("active.site");

        if (StringUtils.hasLength(site)) {
            list.add(site);
        }
        return list;
    }

    public Map<String, Object> dbTableListToSourceMap() {
        log.info(Marker.BOOT_CONFIG, "##############################################################");

        Map<String, Object> sourceMap = new HashMap<>();
        List<String> profiles = activeProfiles();
        List<String> sites = activeSite();

        try {
            for (String site : sites) {
                for (String profile : profiles) {
                    List<DatabaseProperty> list = loadPropertySource(sqlSession, site, profile);
                    for (DatabaseProperty propertySource : list) {
                        String key = propertySource.getPropertyKey();
                        String value = propertySource.getPropertyValue();
                        sourceMap.put(key, value);
                    }
                }
            }
        } catch (Throwable e) {
            if (e instanceof PersistenceException) {
                log.warn("{}", e.getMessage());
                log.warn("{}", e.getCause());
                log.warn("{}", e.getLocalizedMessage());
            }

            log.warn(Marker.BOOT_CONFIG, "# FAILED TO LOAD DATABASE PROPERTIES - {}", e.getMessage());
            log.warn(Marker.BOOT_CONFIG, "# DATABASE PROPERTIES not ACTIVE !!!!");
        }

        log.info(Marker.BOOT_CONFIG, "##############################################################");
        return sourceMap;
    }

    public List<DatabaseProperty> loadPropertySource(SqlSession sqlSession, String site, String profile) throws Throwable {
        DatabaseProperty conditions = conditions(site, profile);
        List<DatabaseProperty> list = new ArrayList<DatabaseProperty>();
        try {
            list = sqlSession.getMapper(DatabasePropertyMapper.class).findAll(conditions);
            log.info(Marker.BOOT_CONFIG, "# LOAD DATABASE PROPERTIES [site:{}, profile:{}] - {}", conditions.getSite(), conditions.getProfile(), list);
        } catch (Throwable e) {
            throw new Throwable(e);
        }

        return list;
    }
}