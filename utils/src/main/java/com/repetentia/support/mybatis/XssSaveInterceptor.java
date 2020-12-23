package com.repetentia.support.mybatis;

import java.lang.reflect.Modifier;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class XssSaveInterceptor implements Interceptor {
    private static final Logger LOGGER = LogManager.getLogger(XssSaveInterceptor.class);
    private static final int MOD = Modifier.STATIC | Modifier.FINAL;
    public static void main(String[] args) {
        System.out.println(Modifier.STATIC);
        System.out.println(Modifier.FINAL);
        System.out.println(MOD);
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Object target = args[1];
//        Field[] fields = target.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            String fieldName = field.getName();
//            if (field.getType().equals(String.class) && ((field.getModifiers() & MOD) == 0)) {
//                String dirty = BeanUtils.getProperty(target, fieldName);
//                BeanUtils.setProperty(target, fieldName, escape(dirty));
//            }
//            ;
//        }
//        Field[] superFields = target.getClass().getSuperclass().getDeclaredFields();
//        for (Field field : superFields) {
//            String fieldName = field.getName();
//            if (field.getType().equals(String.class) && ((field.getModifiers() & MOD) == 0)) {
//                String dirty = BeanUtils.getProperty(target, fieldName);
//                BeanUtils.setProperty(target, fieldName, escape(dirty));
//            }
//            ;
//        }
        return invocation.proceed();
    }

//    private String escape(String dirty) {
//        String clean = XssPreventer.escape(StringUtils.trimWhitespace(dirty));
//        return clean;
//    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        LOGGER.trace("MyBatis Properties - {}", properties);
    }
}
