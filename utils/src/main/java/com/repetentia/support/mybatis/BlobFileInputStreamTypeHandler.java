package com.repetentia.support.mybatis;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BlobFileInputStreamTypeHandler extends BaseTypeHandler<InputStream> {

	/**
	 * Set an {@link InputStream} into {@link PreparedStatement}.
	 * 
	 * @see PreparedStatement#setBlob(int, InputStream)
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, InputStream parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setBlob(i, parameter);
	}

	/**
	 * Get an {@link InputStream} that corresponds to a specified column name from
	 * {@link ResultSet}.
	 * 
	 * @see ResultSet#getBlob(String)
	 */
	@Override
	public InputStream getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return toInputStream(rs.getBlob(columnName));
	}

	/**
	 * Get an {@link InputStream} that corresponds to a specified column index from
	 * {@link ResultSet}.
	 * 
	 * @see ResultSet#getBlob(int)
	 */
	@Override
	public InputStream getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return toInputStream(rs.getBlob(columnIndex));
	}

	/**
	 * Get an {@link InputStream} that corresponds to a specified column index from
	 * {@link CallableStatement}.
	 * 
	 * @see CallableStatement#getBlob(int)
	 */
	@Override
	public InputStream getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return toInputStream(cs.getBlob(columnIndex));
	}

	private InputStream toInputStream(Blob blob) throws SQLException {
		if (blob == null) {
			return null;
		} else {
			return blob.getBinaryStream();
		}
	}

}
