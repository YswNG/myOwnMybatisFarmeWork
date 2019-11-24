package com.ysw.mybatisframework.builder;

import com.ysw.mybatisframework.mapping.MappedStatement;
import com.ysw.mybatisframework.sqlSource.SqlSource;
import com.ysw.mybatisframework.session.Configuration;
import org.dom4j.Element;

import java.util.Map;

public class XMLStatementBuilder extends BaseBuilder{
    private final Element curdElement;

    public XMLStatementBuilder(Configuration configuration, Element e) {
        super(configuration);
        this.curdElement = e;
    }

    public void parseStatement(String namespace) {
        String statementId = curdElement.attributeValue("id");
        if(statementId != null && !statementId.equals("")){
            statementId = namespace + "." +statementId;
            String parameterType = curdElement.attributeValue("parameterType");
            Class<?> parameterTypeClass = this.resolveClass(parameterType);
            String resultType = curdElement.attributeValue("resultType");
            Class<?> resultTypeClass = this.resolveClass(resultType);
            SqlSource sqlSource = this.createSqlSource(this.configuration,curdElement,parameterTypeClass);
            MappedStatement.Builder builder = new MappedStatement
                    .Builder(statementId,sqlSource)
                    .parameterTypeClass(parameterTypeClass)
                    .resultTypeClass(resultTypeClass);
            MappedStatement mappedStatement = builder.build();
            this.configuration.addMappedStatement(statementId,mappedStatement);
        }
    }

    private SqlSource createSqlSource(Configuration configuration, Element curdElement, Class<?> parameterTypeClass) {
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration,curdElement,parameterTypeClass);
        return builder.parseScript();
    }


}
