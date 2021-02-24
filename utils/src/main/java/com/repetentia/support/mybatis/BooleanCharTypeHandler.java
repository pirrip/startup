package com.repetentia.support.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(Boolean.class)
@MappedJdbcTypes(JdbcType.CHAR)
public class BooleanCharTypeHandler extends BaseTypeHandler<Boolean> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        parameter = (parameter == null) ? false : parameter;
        ps.setString(i, parameter ? "T" : "F");
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getBoolean(rs.getString(columnName));
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getBoolean(rs.getString(columnIndex));
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getBoolean(cs.getString(columnIndex));
    }

    private Boolean getBoolean(String s) {
        return "T".equals(s);
    }
}
