package com.ysw.mybatisframework.sqlSource;

import com.ysw.mybatisframework.mapping.BoundSql;

public interface SqlSource {
    BoundSql getBoundSql(Object object);
}
