package com.fcy.logservice.mapper.typehandle;

import com.fcy.logservice.enums.OperationEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Describe: 操作枚举类类型转换器
 * @Author: fuchenyang
 * @Date: 2021/3/12 22:45
 */
@MappedTypes(value = OperationEnum.class)
public class OperationEnumTypeHandle extends BaseTypeHandler<OperationEnum> {

    private Class<OperationEnum> type;

    public OperationEnumTypeHandle(Class<OperationEnum> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, OperationEnum operationEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, operationEnum.getType());
    }

    @Override
    public OperationEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String oper = resultSet.getString(s);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return convert(oper);
        }
    }

    @Override
    public OperationEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String oper = resultSet.getString(i);
        if (resultSet.wasNull()) {
            return null;
        } else {
            return convert(oper);
        }
    }

    @Override
    public OperationEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String oper = callableStatement.getString(i);
        if (callableStatement.wasNull()) {
            return null;
        } else {
          return convert(oper);
        }
    }

    private OperationEnum convert(String oper) {
        OperationEnum[] dbEnums = type.getEnumConstants();
        for (OperationEnum operEnum : dbEnums) {
            if (operEnum.getType().equalsIgnoreCase(oper)) {
                return operEnum;
            }
        }
        return null;
    }
}
