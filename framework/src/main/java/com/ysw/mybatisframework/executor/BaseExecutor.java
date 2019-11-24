package com.ysw.mybatisframework.executor;

import com.ysw.mybatisframework.mapping.BoundSql;
import com.ysw.mybatisframework.mapping.MappedStatement;
import com.ysw.mybatisframework.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseExecutor implements Executor {

    private Map<String, List<Object>> oneLevelCache = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param) {
        // 调用SqlSource获取BoundSql
        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(param);
        // 处理一级缓存
        List<Object> results = oneLevelCache.get(boundSql.getSql());
        if (results != null) {
            return (List<T>) results;
        }

        // 查询数据库
        results = queryFromDataBase(mappedStatement, configuration, param, boundSql);

        oneLevelCache.put(boundSql.getSql(), results);
        return (List<T>) results;
    }

    public abstract List<Object> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration,
                                                   Object param, BoundSql boundSql);

}
