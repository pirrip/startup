package com.repetentia.component.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.repetentia.support.log.Marker;
import com.repetentia.support.sql.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class }) })
public class QueryLogInterceptor implements Interceptor {
    private final List<String> hideList = new ArrayList<String>();
    private final static Pattern PATTERN = Pattern.compile("\\?");

    public QueryLogInterceptor() {
        hideList.add("kr.co.ydns.grip.admin.page.system.mapper.ComScrnRqrMapper");
        hideList.add("kr.co.ydns.grip.admin.page.system.mapper.ComMenuMapper.list");
    }

    private boolean showSql(String id) {
        return !contains(id);
    }

    private boolean contains(String id) {
        if (id == null)
            return false;
        for (String fqdn : hideList) {
            if (id.startsWith(fqdn)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        String id = ms.getId();

        if (showSql(id)) {
            try {
                log.info(Marker.SQL, "[ ID] {}", id);
                Object param = (Object) args[1];
                SqlSource sqlSource = ms.getSqlSource();
                BoundSql boundSql = sqlSource.getBoundSql(param);
                String sql = boundSql.getSql();
                String formattedSQL = new SqlFormatter().format(sql);
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

                Matcher matcher = PATTERN.matcher(formattedSQL);
                StringBuffer result = new StringBuffer();
                Object value = null;

                for (ParameterMapping parameterMapping : parameterMappings) {
                    matcher.find();
                    String property = parameterMapping.getProperty();
                    if (param == null) {
                        // do nothing
                    } else if (param instanceof Map) {
                        int idx = 0;
                        Map<?, ?> paramMap = (Map<?, ?>) param;

                        try {
                            if (property.indexOf(".") > -1) {

                                if (property.startsWith("__frch")) {
                                    String[] props = property.split("\\.");
                                    Set<?> keySet = paramMap.keySet();
                                    for (Object key : keySet) {
                                        Object tmp = paramMap.get(key);
                                        if (tmp instanceof Collection) {
                                            Collection<?> list = (Collection<?>) tmp;
                                            for (Object o : list) {
                                                if (o instanceof String || o instanceof Integer) {
                                                    value = o;
                                                } else {
                                                    value = ReflectionUtils.getPropertyObject(o, props[1]);
                                                }
                                            }
                                        } else if (tmp instanceof Object[]) {
                                            Object[] list = (Object[]) tmp;
                                            for (Object o : list) {
                                                if (o instanceof String || o instanceof Integer) {
                                                    value = o;
                                                } else {
                                                    value = ReflectionUtils.getPropertyObject(o, props[1]);
                                                }
                                            }
                                        }
                                    }

                                } else {
                                    String[] props = property.split("\\.");
                                    Object instance = paramMap.get(props[0]);
                                    value = ReflectionUtils.getPropertyObject(instance, props[1]);
                                }

                            } else {
                                value = paramMap.get(property);
                            }

                        } catch (BindingException e) {
                            log.trace(Marker.SQL, "{}", e);
                            idx++;
                            value = paramMap.get("param" + idx);
                        }

                    } else {
                        value = ReflectionUtils.getPropertyObject(param, property);
                        value = (value == null) ? param : value;
                    }

                    if (value != null) {
                        Class<?> clazz = value.getClass();
                        if (clazz.equals(java.lang.String.class)) {
                            value = "'" + value + "'";
                        }
                        matcher.appendReplacement(result, value.toString());
                    } else {
                        matcher.appendReplacement(result, "null");
                    }
                }
                matcher.appendTail(result);
                if (result.length() > 1048576) {
                    log.debug(Marker.SQL, "[query] {}", formattedSQL);
                } else {
                    log.debug(Marker.SQL, "[query] {}", result);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                log.warn(Marker.SQL, "[ ID] {} FAILED TO LOG - {}", id, e);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
