package com.repetentia.component.table;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

import com.repetentia.utils.BeanUtils;

public class TableDmlProvider {

    public <T> String insert(T t) {
        Class<? extends Object> clazz = t.getClass();
        String tableName = BeanUtils.getTableName(clazz);

        SQL sql = new SQL();

        sql.INSERT_INTO(tableName);

        List<Field> fields = BeanUtils.getAllFields(clazz);
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                String name = column.name();
                sql.INTO_COLUMNS(name);
                sql.INTO_VALUES(String.format("#{%s}", field.getName()));
            }
        }

        String query = sql.toString();
        return query;
    }

    public <T> String update(T t) {
        Class<? extends Object> clazz = t.getClass();
        String tableName = BeanUtils.getTableName(clazz);

        SQL sql = new SQL();
        sql.UPDATE(tableName);

        List<Field> fields = BeanUtils.getAllFields(clazz);
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            if (id != null) {
                String name = column.name();
                sql.WHERE(String.format("%s = #{%s}", name, field.getName()));
            } else {
                String name = column.name();
                field.setAccessible(true);
                try {
                    Object o = field.get(t);
                    if (hasValue(o)) {
                        sql.SET(String.format("%s = #{%s}", name, field.getName()));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }

        return sql.toString();
    }

    public boolean hasValue(Object o) {
        if (o == null)
            return false;
        return true;
    }

    public <T> String updateAll(T t) {
        Class<? extends Object> clazz = t.getClass();
        String tableName = BeanUtils.getTableName(clazz);

        SQL sql = new SQL();
        sql.UPDATE(tableName);

        List<Field> fields = BeanUtils.getAllFields(clazz);
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            if (id != null) {
                String name = column.name();
                sql.WHERE(String.format("%s = #{%s}", name, field.getName()));
            } else {
                String name = column.name();
                sql.SET(String.format("%s = #{%s}", name, field.getName()));
            }
        }

        return sql.toString();
    }

    public <T> String delete(T t) {
        Class<? extends Object> clazz = t.getClass();
        String tableName = BeanUtils.getTableName(clazz);

        SQL sql = new SQL();
        sql.DELETE_FROM(tableName);

        List<Field> fields = BeanUtils.getAllFields(clazz);
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            if (id != null) {
                String name = column.name();
                sql.WHERE(String.format("%s = #{%s}", name, field.getName()));
            }
        }

        return sql.toString();
    }

    public <T> String find(T t) {
        Class<? extends Object> clazz = t.getClass();
        String tableName = BeanUtils.getTableName(clazz);

        SQL sql = new SQL();
        sql.FROM(tableName);

        List<Field> fields = BeanUtils.getAllFields(clazz);
        for (Field field : fields) {
            String alias = field.getName();
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            String name = column.name();
            if (id != null) {
                sql.WHERE(String.format("%s = #{%s}", name, alias));
            }
            sql.SELECT(String.format("%s as %s", name, alias));
        }
        return sql.toString();
    }

    public <T> String findAll(T t) {
        Class<? extends Object> clazz = t.getClass();
        String tableName = BeanUtils.getTableName(clazz);

        SQL sql = new SQL();
        sql.FROM(tableName);

        List<Field> fields = BeanUtils.getAllFields(clazz);
        for (Field field : fields) {
            String alias = field.getName();
            Column column = field.getAnnotation(Column.class);
            String name = column.name();

            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (!ObjectUtils.isEmpty(value)) {
                    sql.WHERE(String.format("%s = #{%s}", name, alias));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
            sql.SELECT(String.format("%s as %s", name, alias));
        }
        return sql.toString().toLowerCase();
    }
}
