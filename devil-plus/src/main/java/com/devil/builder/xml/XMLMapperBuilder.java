package com.devil.builder.xml;

import com.devil.io.Resources;
import com.devil.mapping.Configuration;
import com.devil.mapping.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-24 16:04
 **/
public class XMLMapperBuilder {
    /**
     * 全局数据源配置信息
     */
    private final Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element root) throws IOException, DocumentException, ClassNotFoundException {
        for (Element element : root.elements()) {
            String resource = element.attributeValue("resource");
            parse(Resources.getResourceAsStream(resource));
        }
    }

    /**
     * 将mapper流文件转换成配置类
     *
     * @param in
     */
    private void parse(InputStream in) throws DocumentException, ClassNotFoundException {
        Element root = new SAXReader().read(in).getRootElement();
        String namespace = root.attributeValue("namespace");
        Class<?> mapperType = Resources.classForName(namespace);
        configuration.addMapper(mapperType);
        for (Element sqlElement : root.elements()) {
            String id = sqlElement.attributeValue("id");
            String parameterType = sqlElement.attributeValue("parameterType");
            String resultType = sqlElement.attributeValue("resultType");
            String sql = sqlElement.getTextTrim();
            String key = namespace + "." + id;
            MappedStatement mappedStatement = new MappedStatement(configuration);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            mappedStatement.setSqlCommandType(sqlElement.getName());
            configuration.addMapperStatement(key, mappedStatement);
        }
    }
}

