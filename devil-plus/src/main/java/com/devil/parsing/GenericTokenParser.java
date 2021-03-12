package com.devil.parsing;

/**
 * @Program: study
 * @Description: 将以指定开头结尾的字符串通过自定义的handler转换成需要的字符串
 * @Author: Devil
 * @Create: 2021-02-25 09:35
 **/
public class GenericTokenParser {

    /**
     * 开始字符串
     */
    private final String openToken;

    /**
     * 结束字符串
     */
    private final String closeToken;

    /**
     * 转换handler
     */
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    /**
     * 将text中的以openToken开始 closeToken结尾的字符串通过handler转换成其它字符串
     *
     * @param text 转换字符串
     * @return 转换后字符串
     */
    public String parse(String text) {
        // 若被字符串为空 返回空字符串
        if (text == null || text.isEmpty()) {
            return "";
        }
        // 寻找以openToken开始的字符
        int start = text.indexOf(openToken);
        // 表示没找到需要替换的内容 返回原字符串
        if (-1 == start) {
            return text;
        }
        // 原字符串转换成字节数组
        char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        // openToken和closeToken中的内容 便于利用handler替换保存
        StringBuilder expression = null;
        // 循环替换token内容
        while (start > -1) {
            // 判断替换token是否被转义 若被转义则删除转义反斜杠 且转义后openToken不再表示替换
            if (start > 0 && src[start - 1] == '\\') {
                // 将转义符之前的所有内容加入到builder中
                builder.append(src, offset, start - offset - 1).append(openToken);
                // 偏移量将openToken跳过
                offset = start + openToken.length();
            } else {
                // 寻找closeToken
                // 重置expression
                if (null == expression) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                // 将openToken之前的所有内容加入到builder中 此时offset是0
                builder.append(src, offset, start - offset);
                // 跳过openToken
                offset = start + openToken.length();
                // 寻找closeToken
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        // 若closeToken被转义则不在表示占位符
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        // 将匹配到的内容加入到expression中
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                // 没找到closeToken
                if (-1 == end) {
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    // 找到结束标签 将内容替换并且加到builder中
                    builder.append(handler.handlerToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            // 找下一个openToken
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }
}
