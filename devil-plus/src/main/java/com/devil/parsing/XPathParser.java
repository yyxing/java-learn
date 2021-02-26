package com.devil.parsing;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 14:37
 **/
public class XPathParser {
    private Element root;


    public XPathParser(InputStream in) {
        try {
            // 将流生成document
            Document document = new SAXReader().read(in);
            // 获取根元素
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取根元素下的某个节点
     *
     * @param expression xpath表达式
     * @return
     */
    public Node evalNode(String expression) {
        return evalNode(root, expression);
    }

    public Node evalNode(Element root, String expression) {
        return root.selectSingleNode(expression);
    }

    /**
     * 获取根元素下的某些节点
     *
     * @param expression xpath表达式
     * @return
     */
    public List<Node> evalNodes(String expression) {
        return evalNodes(root, expression);
    }

    public List<Node> evalNodes(Element root, String expression) {
        return root.selectNodes(expression);
    }

    public Properties getChildrenAsProperties(Element root) {
        Properties properties = new Properties();
        for (Element child : root.elements()) {
            String name = child.attributeValue("name");
            String value = child.attributeValue("value");
            if (null != name && null != value) {
                properties.setProperty(name, value);
            }
        }
        return properties;
    }
}
