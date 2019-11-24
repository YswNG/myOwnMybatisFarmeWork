package com.ysw.mybatisframework.builder;


import com.ysw.mybatisframework.io.Resource;
import com.ysw.mybatisframework.mapping.Environment;
import com.ysw.mybatisframework.session.Configuration;
import com.ysw.mybatisframework.utils.DocumentUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigurationBuilder extends BaseBuilder{
    private final Document document;

    public XMLConfigurationBuilder(InputStream inputStream){
        super(new Configuration());
        this.document = DocumentUtils.readDocument(inputStream);
    }

    /**
     * 解析全局配置文件
     * @return
     */
    public Configuration parse() {
        this.parseConfiguration(document.getRootElement());
        return this.configuration;
    }

    private void parseConfiguration(Element rootElement) {
//        this.propertiesElement(rootElement.element("properties"));                    解析properties节点
//        Properties properties = this.settingsAsProperties(rootElement.element("settings"));  解析settings
//        this.parseTypeAliasesElement(rootElement.element("typeAliases"));             解析typeAliases
//        this.parsePlugins(rootElement.element("plugins"));                            解析plugins
        try {
            this.parseEnvironmentsElement(rootElement.element("environments"));
            this.parseMappersElement(rootElement.element("mappers"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析environments 节点 包括datasource
     * @param environmentsElement
     */
    private void parseEnvironmentsElement(Element environmentsElement) throws Exception{

        if(environmentsElement != null){
            String defaultEnv = environmentsElement.attributeValue("default");
            if(defaultEnv == null || defaultEnv == ""){
                return ;
            }
            List<Element> elements = environmentsElement.elements("environment");
            for(Element e : elements){
                String id = e.attributeValue("id");
                if(defaultEnv.equals(id)){
                    DataSource datasource = this.parseDataSourceElement(e.element("dataSource"));
                    Environment.Builder builder = new Environment.Builder(id).dataSource(datasource);
                    this.configuration.setEnvironment(builder.build());
                }
            }
        }
    }

    /**
     * 解析datasource数据源
     * @param
     */
    private DataSource parseDataSourceElement(Element dbElement) {
        String type = dbElement.attributeValue("type");
        BasicDataSource dataSource = new BasicDataSource();
        if("DBCP".equals(type)){

            Properties properties = new Properties();
            List<Element> propElements = dbElement.elements();
            for(Element e : propElements){
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                properties.put(name,value);
            }

            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
        }
        return dataSource;
    }

    /**
     * 解析mappers 节点
     * @param mappersElement
     */
    private void parseMappersElement(Element mappersElement) throws Exception{
        if(mappersElement != null){
            List<Element> mappers = mappersElement.elements();
            for(Element mapper:mappers){
                String resource = mapper.attributeValue("resource");
                String url = mapper.attributeValue("url");
                String mapperClass = mapper.attributeValue("class");
                XMLMapperBuilder parse ;
                InputStream inputStream ;
                if(resource != null && url == null && mapperClass == null){
                    inputStream = Resource.getResourceAsStream(resource);
                    parse = new XMLMapperBuilder(this.configuration,inputStream);
                    parse.parse();
                }else if(resource == null && url != null && mapperClass == null){
                    //TODO
                }else{
                    if (resource != null || url != null || mapperClass == null) {
                        throw new Exception("A mapper element may only specify a url, resource or class, but not more than one.");
                    }
                }
            }
        }
    }

}
