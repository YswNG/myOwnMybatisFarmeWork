package com.ysw.mybatisframework.session;

import com.ysw.mybatisframework.builder.XMLConfigurationBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream){
        XMLConfigurationBuilder parse = new XMLConfigurationBuilder(inputStream);
        return this.build(parse.parse());
    }

    private SqlSessionFactory build(Configuration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
}
