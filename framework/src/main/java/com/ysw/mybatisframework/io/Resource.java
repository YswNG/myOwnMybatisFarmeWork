package com.ysw.mybatisframework.io;

import java.io.InputStream;

public class Resource {

    public static InputStream getResourceAsStream(String resource){
        return Resource.class.getClassLoader().getResourceAsStream(resource);
    }
}
