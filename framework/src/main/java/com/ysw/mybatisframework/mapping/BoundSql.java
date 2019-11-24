package com.ysw.mybatisframework.mapping;

import com.ysw.mybatisframework.sqlSource.ParameterMapping;

import java.util.List;

public class BoundSql {
    private final String sql;
    private final List<ParameterMapping> parameterMappings;

    public BoundSql(String sql , List<ParameterMapping> parameterMappings){
        this.sql = sql;
        this.parameterMappings=parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

}
