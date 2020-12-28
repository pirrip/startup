package com.repetentia.web.table.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface TableMapper<T> {
    @InsertProvider(type = TableDmlProvider.class, method = "insert")
    public int insert(T t);
    @UpdateProvider(type = TableDmlProvider.class, method = "update")
    public int update(T t);
    @DeleteProvider(type = TableDmlProvider.class, method = "delete")
    public int delete(T t);
    @SelectProvider(type = TableDmlProvider.class, method = "find")
    public T find(T t);
    @SelectProvider(type = TableDmlProvider.class, method = "findAll")
    public List<T> findAll(T t);
}
