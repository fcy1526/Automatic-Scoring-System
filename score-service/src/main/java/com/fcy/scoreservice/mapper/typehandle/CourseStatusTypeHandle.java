package com.fcy.scoreservice.mapper.typehandle;

import com.fcy.scoreservice.enums.CourseStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/28 22:26
 */
@MappedTypes(value = CourseStatus.class)
public class CourseStatusTypeHandle extends BaseTypeHandler<CourseStatus> {

    private Class<CourseStatus> type;

    public CourseStatusTypeHandle(Class<CourseStatus> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CourseStatus status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, status.getStatus());
    }

    @Override
    public CourseStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String status = resultSet.getString(s);
        if(resultSet.wasNull()){
            return null;
        }
        else{
            return convert(status);
        }
    }

    @Override
    public CourseStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String status = resultSet.getString(i);
        if(resultSet.wasNull()){
            return null;
        }
        else{
            return convert(status);
        }
    }

    @Override
    public CourseStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String status = callableStatement.getString(i);
        if(callableStatement.wasNull()){
            return null;
        }
        else{
            return convert(status);
        }
    }

    private CourseStatus convert(String status) {
        CourseStatus[] dbEnums = type.getEnumConstants();
        for (CourseStatus course : dbEnums) {
            if(course.getStatus().equals(status)){
                return course;
            }
        }
        return null;
    }
}
