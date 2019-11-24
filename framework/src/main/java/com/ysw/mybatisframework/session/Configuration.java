package com.ysw.mybatisframework.session;



import com.ysw.mybatisframework.mapping.Environment;
import com.ysw.mybatisframework.mapping.MappedStatement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private Environment environment;
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void addMappedStatement(String id,MappedStatement mappedStatement){
        this.mappedStatements.put(id,mappedStatement);
    }

    public MappedStatement getMappedStatementById(String statementId) {
        return mappedStatements.get(statementId);
    }


}
