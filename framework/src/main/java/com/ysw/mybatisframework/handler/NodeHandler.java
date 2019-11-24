package com.ysw.mybatisframework.handler;

import com.ysw.mybatisframework.sqlNode.SqlNode;
import org.dom4j.Element;

import java.util.List;

public interface NodeHandler {
    void handleNode(Element nodeToHandle, List<SqlNode> contents);
}
