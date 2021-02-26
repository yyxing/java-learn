package com.devil.builder.xml;

import com.devil.mapping.Configuration;
import com.devil.parsing.XPathParser;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.IOException;
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

    /**
     * 解析数据源的配置文件至Configuration
     * @return {@link Configuration} 配置类
     */
    public Configuration parse() {
        if (parsed) {
            throw new RuntimeException("Each XMLConfigBuilder can only be used once.");
        }
        parsed = true;
        parseConfiguration((Element) parser.evalNode("//configuration"));
        return configuration;
    }

    /**
     * 解析至配置类中
     * @param root 根节点
     */
    private void parseConfiguration(Element root) {
        try {
            parseProperties((Element) root.selectSingleNode("//properties"));
            parseDatasource((Element) root.selectSingleNode("//datasource"));
            parseMapper((Element) root.selectSingleNode("//mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    /**
     * 解析Mapper标签
     * @param root 根节点
     */
    private void parseMapper(Element root) throws IOException, DocumentException, ClassNotFoundException {
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        xmlMapperBuilder.parse(root);
    }

    /**
     * 解析数据源
     * @param root 根节点
     */
    private void parseDatasource(Element root) {
        Properties properties = parser.getChildrenAsProperties(root);
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(properties.getProperty("username"));
        hikariConfig.setPassword(properties.getProperty("password"));
        hikariConfig.setJdbcUrl(properties.getProperty("url"));
        hikariConfig.setDriverClassName(properties.getProperty("driver"));
        configuration.setDataSource(new HikariDataSource(hikariConfig));
    }

    /**
     * 解析properties 解析配置属性
     * @param root 根节点
     */
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
