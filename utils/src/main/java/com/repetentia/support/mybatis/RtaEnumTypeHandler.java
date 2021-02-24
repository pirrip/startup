package com.repetentia.support.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.repetentia.support.json.RtaEnumType;

public class RtaEnumTypeHandler extends BaseTypeHandler<RtaEnumType> {
    private final Class<RtaEnumType> type;

    public RtaEnumTypeHandler(Class<RtaEnumType> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RtaEnumType parameter, JdbcType jdbcType)
            throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter.code());
        } else {
            ps.setObject(i, parameter.code(), jdbcType.TYPE_CODE); // see r3589
        }
    }

    @Override
    public RtaEnumType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : Enum.valueOf(type, s);
    }

    @Override
    public RtaEnumType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public RtaEnumType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }

}
