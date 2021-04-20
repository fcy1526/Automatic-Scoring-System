package com.fcy.userservice.mapper.typehandle;

import com.fcy.userservice.enums.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Describe: 性别枚举映射
 * @Author: fuchenyang
 * @Date: 2021/2/14 21:22
 */
///* 数据库中的数据类型 */
//@MappedJdbcTypes(JdbcType.VARCHAR)
//
/* 转化后的数据类型 */
@MappedTypes(value = SexEnum.class)
public class SexTypeHandle extends BaseTypeHandler<SexEnum> {

    private Class<SexEnum> type;

    public SexTypeHandle(Class<SexEnum> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SexEnum sexEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, sexEnum.getSex());
    }

    @Override
    public SexEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String sex = resultSet.getString(s);
        if(resultSet.wasNull()){
            return null;
        }
        else{
            return convert(sex);
        }
    }

    @Override
    public SexEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sex = resultSet.getString(i);
        if(resultSet.wasNull()){
            return null;
        }
        else{
            return convert(sex);
        }
    }

    @Override
    public SexEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sex = callableStatement.getString(i);
        if(callableStatement.wasNull()){
            return null;
        }
        else{
            return convert(sex);
        }
    }

    private SexEnum convert(String sex) {
        SexEnum[] dbEnums = type.getEnumConstants();
        for (SexEnum sexEnum : dbEnums) {
            if(sexEnum.getSex().equals(sex)){
                return sexEnum;
            }
        }
        return null;
    }
}
