package com.ysw.mybatisframework.executor;

import com.ysw.mybatisframework.mapping.MappedStatement;
import com.ysw.mybatisframework.session.Configuration;

import java.util.List;

public class CachingExecutor implements Executor {
    private Executor delegate;

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param) {
        // 处理二级缓存
        return delegate.query(mappedStatement, configuration, param);
    }
}
