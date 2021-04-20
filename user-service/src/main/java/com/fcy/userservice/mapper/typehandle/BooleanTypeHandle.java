package com.fcy.userservice.mapper.typehandle;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Describe: 布尔类型转换类 string->布尔
 * @Author: fuchenyang
 * @Date: 2021/3/5 21:19
 */
public class BooleanTypeHandle extends BaseTypeHandler<Boolean> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Boolean aBoolean, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, aBoolean.toString());
    }

    @Override
    public Boolean getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String bool = resultSet.getString(s);
        if(resultSet.wasNull()){
            return null;
        } else{
            return convert(bool);
        }
    }

    @Override
    public Boolean getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String bool = resultSet.getString(i);
        if(resultSet.wasNull()){
            return null;
        } else{
            return convert(bool);
        }
    }

    @Override
    public Boolean getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String bool = callableStatement.getString(i);
        if(callableStatement.wasNull()){
            return null;
        } else{
            return convert(bool);
        }
    }

    private Boolean convert(String bool) {
        return Boolean.parseBoolean(bool);
    }

}
