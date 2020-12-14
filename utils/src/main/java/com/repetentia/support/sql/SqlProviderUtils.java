package com.repetentia.support.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

public class SqlProviderUtils {
    private static String tableName;

    public static void setTableName(String tablename) {
        tableName = tablename;
    }

    public static String table(Class<?> clazz) {
        if (tableName == null) {
            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Table) {
                    String tableName = ((Table) annotation).name();
                    return tableName;
                }
            }
            return null;
        } else {
            return tableName;
        }
    }

    public static List<String> columns(Class<?> clazz) {
        List<String> list = new ArrayList<>();
        Field[] fields = ReflectionUtils.getDeclaredFields(clazz);

        for (Field field : fields) {
            StringBuilder sb = new StringBuilder();
            Annotation[] annotations = field.getAnnotations();
            String fieldName = field.getName();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Column) {
                    String column = ((Column) annotation).name();
                    sb.append(column);
                    sb.append(" as ");
                    sb.append(fieldName);
                }
            }
            list.add(sb.toString());
        }
        return list;
    }

    public static List<String> keyColumns(Class<?> clazz, boolean isCondition) {
        List<String> list = new ArrayList<>();
        Field[] fields = ReflectionUtils.getDeclaredFields(clazz);
        for (Field field : fields) {
            StringBuilder sb = new StringBuilder();
            Annotation[] annotations = field.getAnnotations();
            String fieldName = field.getName();
            String column = null;
            boolean isId = false;
            for (Annotation annotation : annotations) {
                if (annotation instanceof Column)
                    column = ((Column) annotation).name();
                if (annotation instanceof Id)
                    isId = true;
            }
            if (!isCondition)
                isId = !isId;
            if (isId && column != null) {
                sb.append(column);
                sb.append(" = #{");
                sb.append(fieldName);
                sb.append("}");
                list.add(sb.toString());
            }
        }
        return list;
    }

    public static Map<String, String> insertColumns(Class<?> clazz) {
        Map<String, String> values = new HashMap<String, String>();
        Field[] fields = ReflectionUtils.getDeclaredFields(clazz);
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            String fieldName = field.getName();
            String column = null;
            for (Annotation annotation : annotations) {
                if (annotation instanceof Column) {
                    column = ((Column) annotation).name();
                    values.put(column, fieldName);
                }
            }
        }
        return values;
    }

    public static <T> List<String> where(T t) {
        List<String> list = new ArrayList<>();
        Field[] fields = ReflectionUtils.getDeclaredFields(t.getClass());
        for (Field field : fields) {
            StringBuilder sb = new StringBuilder();
            Annotation[] annotations = field.getAnnotations();
            String fieldName = field.getName();
            String column = null;

            for (Annotation annotation : annotations) {
                if (annotation instanceof Column)
                    column = ((Column) annotation).name();
            }
            boolean isCondition = ReflectionUtils.isNotNull(t, fieldName);
            if (isCondition && column != null) {
                sb.append(column);
                sb.append(" = #{");
                sb.append(fieldName);
                sb.append("}");
                list.add(sb.toString());
            }
        }
        return list;
    }

    public static String columnByField(String se, Class<?> clazz) {
        String column = null;
        Field[] fields = ReflectionUtils.getDeclaredFields(clazz);
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            String fieldName = field.getName();
            if (se.equalsIgnoreCase(fieldName)) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Column) {
                        column = ((Column) annotation).name();
                    }
                }
            }

        }
        return column;
    }
}
