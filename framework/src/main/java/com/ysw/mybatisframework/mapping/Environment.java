package com.ysw.mybatisframework.mapping;

import javax.sql.DataSource;

/**
 * 尝试用构造者模式 构建environment
 */
public final class Environment {
    private final String id;
    private final DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public Environment(String id, DataSource dataSource){
        this.id = id;
        this.dataSource = dataSource;
    }
    //创建一个内部静态类Builder 并复制外部对象所有参数
    public static class Builder{
        private String id;
        private DataSource dataSource;
        //Builder构造public 参数为Environment 必填项
        public Builder(String id){
            this.id=id;
        }
        //设置方法 对Environment可选项进行设置 返回Builder实例
        public Environment.Builder dataSource(DataSource dataSource){
            this.dataSource = dataSource;
            return this;
        }

        public String id(){
            return this.id;
        }
        //创建build() 构建并返回 Environment 实例
        public Environment build(){
            return new Environment(this.id,this.dataSource);
        }
    }
}
