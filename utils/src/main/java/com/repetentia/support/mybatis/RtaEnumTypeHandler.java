package com.repetentia.support.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.repetentia.component.code.RtaEnumType;


public class RtaEnumTypeHandler<E extends RtaEnumType> extends BaseTypeHandler<RtaEnumType> {
    private final Class<E> type;

    public RtaEnumTypeHandler(Class<E> type) {
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
            ps.setObject(i, parameter.code(), jdbcType.TYPE_CODE);
        }

    }

    @Override
    public RtaEnumType getNullableResult(ResultSet rs, String columnName) throws SQLException {
      String s = rs.getString(columnName);
      E [] es = type.getEnumConstants();
      for (E e: es) {
          RtaEnumType r = (RtaEnumType)e;
          if (r.code().equals(s)) return e;
      }
      return null;
    }

    @Override
    public RtaEnumType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        E [] es = type.getEnumConstants();
        for (E e: es) {
            RtaEnumType r = (RtaEnumType)e;
            if (r.code().equals(s)) return e;
        }
        return null;
    }

    @Override
    public RtaEnumType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        E [] es = type.getEnumConstants();
        for (E e: es) {
            RtaEnumType r = (RtaEnumType)e;
            if (r.code().equals(s)) return e;
        }
        return null;
    }
}
