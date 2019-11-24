package com.ysw.mybatisframework.executor;

import com.ysw.mybatisframework.mapping.MappedStatement;
import com.ysw.mybatisframework.session.Configuration;

import java.util.List;

public interface Executor {

    <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param);
}
