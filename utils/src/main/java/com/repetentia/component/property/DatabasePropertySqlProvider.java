package com.repetentia.component.property;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.jdbc.SQL;

import com.repetentia.support.sql.SqlProviderUtils;

public class DatabasePropertySqlProvider {
    private static final Class<DatabaseProperty> clazz = DatabaseProperty.class;

    public String findAll(DatabaseProperty condition) {
        SQL sql = new SQL();
        String table = SqlProviderUtils.table(clazz);
        sql.FROM(table);
        List<String> list = SqlProviderUtils.columns(clazz);
        sql.SELECT(list.toArray(new String[list.size()]));
        List<String> conditions = SqlProviderUtils.where(condition);
        sql.WHERE(conditions.toArray(new String[conditions.size()]));
        String propertyKey = SqlProviderUtils.columnByField("propertyKey", clazz);
        sql.ORDER_BY(propertyKey);
        String query = sql.toString();
        return query;
    }

    public String findSe() {
        SQL sql = new SQL();
        String table = SqlProviderUtils.table(clazz);
        sql.SELECT_DISTINCT(distinct());
        sql.FROM(table);
        return sql.toString();
    }

    private String distinct() {
        StringBuilder sb = new StringBuilder();
        String se = SqlProviderUtils.columnByField("se", clazz);
        sb.append(se);
        sb.append(" as se");
        return sb.toString();
    }

    public String update(DatabaseProperty gripPropertySource) {
        SQL sql = new SQL();
        String table = SqlProviderUtils.table(clazz);
        List<String> sets = SqlProviderUtils.keyColumns(clazz, false);
        List<String> conditions = SqlProviderUtils.keyColumns(clazz, true);
        sql.UPDATE(table);
        sql.SET(sets.toArray(new String[sets.size()]));
        sql.WHERE(conditions.toArray(new String[conditions.size()]));
        return sql.toString();
    }

    public String insert(DatabaseProperty gripPropertySource) {
        SQL sql = new SQL();
        String table = SqlProviderUtils.table(clazz);
        Map<String, String> columns = SqlProviderUtils.insertColumns(clazz);
        sql.INSERT_INTO(table);
        Set<Entry<String, String>> set = columns.entrySet();
        for (Entry<String, String> entry : set) {
            sql.VALUES(entry.getKey(), String.format("#{%s}", entry.getValue()));
        }
        return sql.toString();
    }

    public String delete(DatabaseProperty gripPropertySource) {
        SQL sql = new SQL();
        String table = SqlProviderUtils.table(clazz);
        List<String> conditions = SqlProviderUtils.keyColumns(clazz, true);
        sql.DELETE_FROM(table);
        sql.WHERE(conditions.toArray(new String[conditions.size()]));
        return sql.toString();
    }
}