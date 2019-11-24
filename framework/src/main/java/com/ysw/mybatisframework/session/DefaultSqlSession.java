package com.ysw.mybatisframework.session;

import com.ysw.mybatisframework.executor.CachingExecutor;
import com.ysw.mybatisframework.executor.Executor;
import com.ysw.mybatisframework.executor.SimpleExecutor;
import com.ysw.mybatisframework.mapping.MappedStatement;

import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<Object> list = this.selectList(statementId, param);
        if (list != null && list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        // 根据statementId获取MappedStatement对象
        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);

        Executor executor = new CachingExecutor(new SimpleExecutor());
        return executor.query(mappedStatement, configuration, param);
    }
}
