package com.devil.mapping;

/**
 * @Program: study
 * @Description:
 * @Author: Devil
 * @Create: 2021-02-25 09:28
 **/
public class ParameterMapping {
    /**
     * 参数信息 也就是对应实体类的某个字段的名称
     */
    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
