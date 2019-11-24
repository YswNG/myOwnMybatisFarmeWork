package com.ysw.mybatisframework.mapping;

import com.ysw.mybatisframework.sqlSource.SqlSource;

public final class MappedStatement {
    private String statementId;
    private Class<?> parameterTypeClass;
    private Class<?> resultTypeClass;
    private String statementType;
    private SqlSource sqlSource;

    public MappedStatement(String statementId,SqlSource sqlSource){
        this.statementType = "prepared";
        this.statementId = statementId;
        this.sqlSource = sqlSource;
    }

    public String getStatementType() {
        return this.statementType;
    }

    public Class<?> getResultTypeClass() {
        return this.resultTypeClass;
    }

    public Class<?> getParameterTypeClass() {
        return this.parameterTypeClass;
    }

    public SqlSource getSqlSource() {
        return this.sqlSource;
    }

    public static class Builder{
        private String statementId;
        private Class<?> parameterTypeClass;
        private Class<?> resultTypeClass;
        private String statementType;
        private SqlSource sqlSource;


        public Builder(String statementId,SqlSource sqlSource){
            this.statementId = statementId;
            this.sqlSource = sqlSource;
            this.statementType = "prepared";
        }

        public MappedStatement.Builder parameterTypeClass(Class<?> parameterType){
            this.parameterTypeClass = parameterType;
            return this;
        }

        public MappedStatement.Builder resultTypeClass(Class<?> resultType){
            this.resultTypeClass = resultType;
            return this;
        }

        public MappedStatement.Builder statementType(String statementType){
            this.statementType = statementType;
            return this;
        }

        public MappedStatement.Builder sqlSource(SqlSource sqlSource){
            this.sqlSource = sqlSource;
            return this;
        }

        public MappedStatement build(){
            return new MappedStatement(this.statementId,this.sqlSource);
        }
    }
}
