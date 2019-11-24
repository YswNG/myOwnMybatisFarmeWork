package com.ysw.mybatisframework.builder;

import com.ysw.mybatisframework.handler.NodeHandler;
import com.ysw.mybatisframework.sqlSource.DynamicSqlSource;
import com.ysw.mybatisframework.sqlSource.RawSqlSource;
import com.ysw.mybatisframework.sqlSource.SqlSource;
import com.ysw.mybatisframework.session.Configuration;
import com.ysw.mybatisframework.sqlNode.*;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XMLScriptBuilder extends BaseBuilder{
    private final Element curdElement;
    private final Class<?> parameterType;
    private boolean isDynamic;

    private Map<String, NodeHandler> nodeHandlerMap = new HashMap<String, NodeHandler>();


    public XMLScriptBuilder(Configuration configuration, Element curdElement, Class<?> parameterType) {
        super(configuration);
        this.curdElement = curdElement;
        this.parameterType = parameterType;
        this.initNodeHandlerMap();
    }

    private void initNodeHandlerMap() {
        this.nodeHandlerMap.put("if", new XMLScriptBuilder.IfHandler());
//        this.nodeHandlerMap.put("trim", new XMLScriptBuilder.TrimHandler());
//        this.nodeHandlerMap.put("where", new XMLScriptBuilder.WhereHandler());
//        this.nodeHandlerMap.put("set", new XMLScriptBuilder.SetHandler());
//        this.nodeHandlerMap.put("foreach", new XMLScriptBuilder.ForEachHandler());
//        this.nodeHandlerMap.put("choose", new XMLScriptBuilder.ChooseHandler());
//        this.nodeHandlerMap.put("when", new XMLScriptBuilder.IfHandler());
//        this.nodeHandlerMap.put("otherwise", new XMLScriptBuilder.OtherwiseHandler());
//        this.nodeHandlerMap.put("bind", new XMLScriptBuilder.BindHandler());
    }

    public SqlSource parseScript() {
        MixedSqlNode rootSqlNode = this.parseDynamicTags(this.curdElement);
        SqlSource sqlSource = null;
        if(this.isDynamic){
            sqlSource = new DynamicSqlSource(this.configuration,rootSqlNode);
        }else{
            sqlSource = new RawSqlSource(this.configuration,rootSqlNode,this.curdElement);
        }
        return (SqlSource)sqlSource;

    }

    private MixedSqlNode parseDynamicTags(Element curdElement) {
        List<SqlNode> contents = new ArrayList<>();
        int nodeCount = curdElement.nodeCount();
        for(int i = 0;i<nodeCount;i++){
            Node node = curdElement.node(i);
            //如果是文本（select * from xxx这种）
            if(node instanceof Text){
                //处理方法 直接获取文本内容就行
                String sqlText = node.getText().trim();
                if(sqlText==null||sqlText.equals("")){
                    continue;
                }
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                if (textSqlNode.isDynamic()) {
                    isDynamic = true;
                    contents.add(textSqlNode);
                } else {
                    contents.add(new StaticTextSqlNode(sqlText));
                }
                //如果是节点节点（<if><where><choose>这种）
            }else if (node instanceof Element) {
                // 此处通过NodeHandler去处理不同的标签
                Element nodeToHandle = (Element) node;
                String name = nodeToHandle.getName();
                // 怎么去查找对应的标签处理器呢，需要通过标签名称
                NodeHandler nodeHandler = nodeHandlerMap.get(name);
                nodeHandler.handleNode(nodeToHandle, contents);

                isDynamic = true;
            }
        }
        return new MixedSqlNode(contents);


    }

    public class IfHandler implements NodeHandler {


        @Override
        public void handleNode(Element nodeToHandler, List<SqlNode> contents) {
            String test = nodeToHandler.attributeValue("test");

            MixedSqlNode parseDynamicTags = parseDynamicTags(nodeToHandler);

            contents.add(new IfSqlNode(test, parseDynamicTags));
        }
    }
}
