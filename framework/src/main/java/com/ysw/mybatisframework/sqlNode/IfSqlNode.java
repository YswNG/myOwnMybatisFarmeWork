package com.ysw.mybatisframework.sqlNode;

import com.ysw.mybatisframework.utils.OgnlUtils;

public class IfSqlNode implements SqlNode {
    private String test;

    private SqlNode rootSqlNode;

    public IfSqlNode(String test, MixedSqlNode rootSqlNode) {
        this.test = test;
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public void apply(DynamicContext context) {
        boolean testValue = OgnlUtils.evaluateBoolean(test, context.getBindings().get("_parameter"));
        if (testValue) {
            rootSqlNode.apply(context);
        }
    }
}
