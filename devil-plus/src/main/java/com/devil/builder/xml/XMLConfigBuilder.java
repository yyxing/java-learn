package com.devil.builder.xml;

import com.devil.mapping.Configuration;
import com.devil.parsing.XPathParser;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-23 14:29
 **/
public class XMLConfigBuilder {
    /**
     * xml解析器
     */
    private final XPathParser parser;

    /**
     * 是否解析过该
     */
    private boolean parsed;

    /**
     * 全局配置类
     */
    private final Configuration configuration;

    public XMLConfigBuilder(InputStream in) {
        // 根据xml流文件解析xml信息
        parser = new XPathParser(in);
        // 初始化Configuration
        configuration = new Configuration();
    }

    public Configuration parse() {
        if (parsed) {
            throw new RuntimeException("Each XMLConfigBuilder can only be used once.");
        }
        parsed = true;
        parseConfiguration((Element) parser.evalNode("//configuration"));
        return configuration;
    }

    private void parseConfiguration(Element root) {
        try {
            parseProperties((Element) root.selectSingleNode("//properties"));
            parseDatasource((Element) root.selectSingleNode("//datasource"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    private void parseDatasource(Element root) {

    }

    private void parseProperties(Element root) {
        if (null != root) {
            Properties defaults = parser.getChildrenAsProperties(root);
            Properties vars = configuration.getVariables();
            if (null != vars) {
                defaults.putAll(vars);
            }
            configuration.setVariables(defaults);
        }
    }

}
