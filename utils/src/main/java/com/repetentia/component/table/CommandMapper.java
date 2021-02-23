package com.repetentia.component.table;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface CommandMapper {
    @InsertProvider(type = TableDmlProvider.class, method = "insert")
    public <T> int insert(T t);
    @UpdateProvider(type = TableDmlProvider.class, method = "update")
    public <T> int update(T t);
    @UpdateProvider(type = TableDmlProvider.class, method = "updateAll")
    public <T> int updateAll(T t);
    @DeleteProvider(type = TableDmlProvider.class, method = "delete")
    public <T> int delete(T t);
    @SelectProvider(type = TableDmlProvider.class, method = "find")
    public <T> T find(T t);
    @SelectProvider(type = TableDmlProvider.class, method = "findAll")
    public <T> List<T> findAll(T t);
}