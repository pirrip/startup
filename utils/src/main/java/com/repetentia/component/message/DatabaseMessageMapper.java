package com.repetentia.component.message;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface DatabaseMessageMapper {
    // 컴포넌트용
    @SelectProvider(type = DatabaseMessageSqlProvider.class, method = "findAll")
    public List<DatabaseMessageSource> findAll();

    @UpdateProvider(type = DatabaseMessageSqlProvider.class, method = "update")
    public int update(DatabaseMessageSource gripMessageSource);

    @InsertProvider(type = DatabaseMessageSqlProvider.class, method = "insert")
    public int insert(DatabaseMessageSource gripMessageSource);

    // 관리자 프로그램용
    @SelectProvider(type = DatabaseMessageSqlProvider.class, method = "findAllPage")
    List<DatabaseMessageSource> findAllPage();

    @DeleteProvider(type = DatabaseMessageSqlProvider.class, method = "delete")
    public int delete(DatabaseMessageSource gripMessageSource);
}
