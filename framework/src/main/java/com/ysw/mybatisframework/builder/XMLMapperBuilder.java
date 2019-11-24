package com.ysw.mybatisframework.builder;

import com.ysw.mybatisframework.session.Configuration;
import com.ysw.mybatisframework.utils.DocumentUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder extends BaseBuilder{
    private final Document document;

    public XMLMapperBuilder(Configuration configuration, InputStream inputStream) {
        super(configuration);
        this.document = DocumentUtils.readDocument(inputStream);
    }

    public void parse() throws Exception{
        Element context = document.getRootElement();
        String namespace = context.attributeValue("namespace");
        if(namespace == null || !namespace.equals("")){
//            this.parseCacheRef(context.element("cache-ref")); 解析cache-ref
//            this.parseCache(context.element("cache"));  解析cache
//            this.parseParameterMap(context.elements("/mapper/parameterMap")); 解析parameterMap 已过时
//            this.parseResultMap(context.elements("/mapper/resultMap"));  //解析resultMap
//            this.parseSql(context.elements("/mapper/sql")); //解析sql片段
            this.buildStatementFromContext(context.elements("select|update|insert|delete"),namespace);
        }else{
            throw new Exception();
        }
    }

    private void buildStatementFromContext(List<Element> elements,String namespace) {
        for(Element e:elements){
            XMLStatementBuilder parse = new XMLStatementBuilder(this.configuration,e);
            try {
                parse.parseStatement(namespace);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
