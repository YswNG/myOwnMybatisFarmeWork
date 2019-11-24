package com.ysw.mybatisframework.builder;

import com.ysw.mybatisframework.session.Configuration;

public abstract class BaseBuilder {
    public final Configuration configuration;

    public BaseBuilder(Configuration configuration){
        this.configuration = configuration;
    }


    public Class<?> resolveClass(String type) {
        try {
            Class<?> clazz = Class.forName(type);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
