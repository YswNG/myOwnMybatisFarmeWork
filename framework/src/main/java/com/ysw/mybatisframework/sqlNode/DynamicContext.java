package com.ysw.mybatisframework.sqlNode;

import java.util.HashMap;
import java.util.Map;

public class DynamicContext {
    private StringBuffer sb;
    private Map<String,Object> bindings = new HashMap<>();


    public DynamicContext(Object parameter) {
        this.bindings.put("_parameter", parameter);
    }

    public String getSql() {
        return sb.toString();
    }

    public void appendSql(String sql) {
        sb.append(sql);
        sb.append(" ");
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }
}
