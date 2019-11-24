package com.ysw.mybatisframework.sqlSource;

import com.ysw.mybatisframework.mapping.BoundSql;

import java.util.List;

public class StaticSqlSource implements SqlSource {

    private String sql;

    private List<ParameterMapping> parameterMappings;

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        super();
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        return new BoundSql(sql, parameterMappings);
    }

}
