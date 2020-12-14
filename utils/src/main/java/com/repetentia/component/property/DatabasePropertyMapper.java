package com.repetentia.component.property;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface DatabasePropertyMapper {
	@SelectProvider(type = DatabasePropertySqlProvider.class, method = "findAll")
	public List<DatabaseProperty> findAll(DatabaseProperty condition);

	@SelectProvider(type = DatabasePropertySqlProvider.class, method = "findSe")
	public List<DatabaseProperty> findSe();

	@UpdateProvider(type = DatabasePropertySqlProvider.class, method = "update")
	public int update(DatabaseProperty propertySource);

	@InsertProvider(type = DatabasePropertySqlProvider.class, method = "insert")
	public int insert(DatabaseProperty propertySource);
	
	@DeleteProvider(type = DatabasePropertySqlProvider.class, method = "delete")
	public int delete(DatabaseProperty propertySource);
}
