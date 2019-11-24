package com.ysw.mybatisframework.sqlSource;

import com.ysw.mybatisframework.mapping.BoundSql;
import com.ysw.mybatisframework.session.Configuration;
import com.ysw.mybatisframework.sqlNode.DynamicContext;
import com.ysw.mybatisframework.sqlNode.MixedSqlNode;
import com.ysw.mybatisframework.sqlNode.SqlNode;

public class DynamicSqlSource implements SqlSource {
    private final Configuration configuration;
    private final SqlNode rootSqlNode;

    public DynamicSqlSource(Configuration configuration, MixedSqlNode rootSqlNode) {
        this.configuration = configuration;
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        DynamicContext context = new DynamicContext(paramObject);
        // 将SqlNode处理成一条SQL语句
        rootSqlNode.apply(context);
        // 该SQL语句，此时还包含#{}，不包含${}
        String sql = context.getSql();
        // 通过SqlSourceParser去解析SqlSource中的#{}
        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        // 将解析的结果，最终封装成StaticSqlSource
        SqlSource sqlSource = sqlSourceParser.parse(sql);
        // 调用StaticSqlSource的方法
        return sqlSource.getBoundSql(paramObject);
    }
}
