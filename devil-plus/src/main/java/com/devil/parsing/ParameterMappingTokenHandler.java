package com.devil.parsing;

import com.devil.mapping.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: study
 * @Description: 该类是将sql中的#{id} 转换成 ?的占位符
 * @Author: Devil
 * @Create: 2021-02-25 09:36
 **/
public class ParameterMappingTokenHandler implements TokenHandler {
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    /**
     * 字符串转换 将指定开头结尾内部的内容保存并转换成所需字符串
     * @param content 转换器字符串
     * @return 转换后字符串
     */
    @Override
    public String handlerToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        return new ParameterMapping(content);
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
