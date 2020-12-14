package com.repetentia.component.message;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.jdbc.SQL;

import com.repetentia.support.sql.SqlProviderUtils;


public class DatabaseMessageSqlProvider {
    private static final Class<DatabaseMessageSource> clazz = DatabaseMessageSource.class;
	public String findAll() {
		SQL sql = new SQL();
		String table = SqlProviderUtils.table(clazz);
		sql.FROM(table);
		List<String> list = SqlProviderUtils.columns(clazz);
		sql.SELECT(list.toArray(new String[list.size()]));
		return sql.toString();
	}

	public String update() {
		SQL sql = new SQL();
		String table = SqlProviderUtils.table(clazz);
		sql.UPDATE(table);
		List<String> sets = SqlProviderUtils.keyColumns(clazz, false);
		for (String set:sets) {
			sql.SET(set);	
		}
		List<String> conditions = SqlProviderUtils.keyColumns(clazz, true);
		sql.WHERE(conditions.toArray(new String[conditions.size()]));
		return sql.toString();		
	}
	
	public String insert() {
		SQL sql = new SQL();
		String table = SqlProviderUtils.table(clazz);
		sql.INSERT_INTO(table);
		Map<String, String> insertColumns = SqlProviderUtils.insertColumns(clazz);
		Set<Entry<String, String>> entrySet = insertColumns.entrySet();
		for (Entry<String, String> entry:entrySet) {
			sql.VALUES(entry.getKey(), "#{" + entry.getValue() + "}");
		}
		return sql.toString();
	}
	
	public String findAllPage() {
		SQL sql = new SQL();
        String table = SqlProviderUtils.table(clazz);
        sql.SELECT_DISTINCT(distinct());
        sql.FROM(table);
		return sql.toString();
	}
	
    private String distinct() {
    	StringBuilder sb = new StringBuilder();
    	String page = SqlProviderUtils.columnByField("page", clazz);
    	sb.append(page);
    	sb.append(" as page");
    	return sb.toString();
    }
    
    public String delete() {
        SQL sql = new SQL();
        String table = SqlProviderUtils.table(clazz);
        List<String> conditions = SqlProviderUtils.keyColumns(clazz, true);
        sql.DELETE_FROM(table);
        sql.WHERE(conditions.toArray(new String[conditions.size()]));
        return sql.toString();
    }
}
