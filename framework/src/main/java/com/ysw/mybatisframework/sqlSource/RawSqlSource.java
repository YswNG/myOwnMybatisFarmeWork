package com.ysw.mybatisframework.sqlSource;

import com.ysw.mybatisframework.mapping.BoundSql;
import com.ysw.mybatisframework.session.Configuration;
import com.ysw.mybatisframework.sqlNode.MixedSqlNode;
import org.dom4j.Element;

public class RawSqlSource implements SqlSource {
    public RawSqlSource(Configuration configuration, MixedSqlNode rootSqlNode, Element curdElement) {

    }

    @Override
    public BoundSql getBoundSql(Object object) {
        return null;
    }
}
