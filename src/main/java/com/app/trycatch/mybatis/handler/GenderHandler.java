package com.app.trycatch.mybatis.handler;


import com.app.trycatch.common.enumeration.member.Gender;
import com.app.trycatch.common.enumeration.member.Status;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Gender.class)
public class GenderHandler implements TypeHandler<Gender> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public Gender getResult(ResultSet rs, String columnName) throws SQLException {
        switch (rs.getString(columnName)) {
            case "man":
                return Gender.MAN;
            case "women":
                return Gender.WOMEN;
        }
        return null;
    }

    @Override
    public Gender getResult(ResultSet rs, int columnIndex) throws SQLException {
        switch (rs.getString(columnIndex)) {
            case "man":
                return Gender.MAN;
            case "women":
                return Gender.WOMEN;
        }
        return null;
    }

    @Override
    public Gender getResult(CallableStatement cs, int columnIndex) throws SQLException {
        switch (cs.getString(columnIndex)) {
            case "man":
                return Gender.MAN;
            case "women":
                return Gender.WOMEN;
        }
        return null;
    }
}
