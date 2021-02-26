package com.devil.utils;

/**
 * @Program: study
 * @Description: 字符串工具类
 * @Author: Devil
 * @Create: 2021-02-25 18:07
 **/
public class StrUtil {

    public static final char UNDERLINE = '_';
    public static final String UNDERLINE_STR = "_";

    /**
     * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     *
     * @param str    转换前的驼峰式命名的字符串，也可以为符号连接形式
     * @param symbol 连接符
     * @return 转换后符号连接方式命名的字符串
     */
    public static String toSymbolCase(CharSequence str, char symbol) {
        // 若为空字符串 返回空字符
        if (null == str || str.length() == 0) {
            return "";
        }
        final int length = str.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        // 转换的条件有3个
        // 1.当前字符为小写前一个字符为大写 添加连接符 xxBa -> xxB_a
        // 2.当前字符为大写前一个字符为小写 添加连接符 xxbA -> xxb_A
        // 3.当多个大写连接时表示多个大写为同一个词语 ABCd -> ABC_d
        for (int i = 0; i < length; i++) {
            c = str.charAt(i);
            // 前一个字符
            final Character preChar = (i > 0) ? str.charAt(i - 1) : null;
            if (Character.isUpperCase(c)) {
                // 若当前字符为大写处理
                // 后一个字符
                final Character nextChar = (i < length - 1) ? str.charAt(i + 1) : null;
                // 若前一个字符存在且大写 表示为同一词 直接添加当前字符到结果串中
                if (null != preChar && Character.isUpperCase(preChar)) {
                    sb.append(c);
                } else if (null != nextChar && Character.isUpperCase(nextChar)) {
                    // 前一个字符不为大写和连接符  且当前字符为大写 在该词前增加连接符
                    // 主要是为了判断当preChar不为大写和空的情况
                    if (null != preChar && preChar != symbol) {
                        sb.append(symbol);
                    }
                    sb.append(c);
                } else {
                    // 前后都为小写或空 当前字符为大写
                    if (null != preChar && symbol != preChar) {
                        sb.append(symbol);
                    }
                    sb.append(Character.toLowerCase(c));
                }
            } else {
                if (sb.length() > 0 && Character.isUpperCase(sb.charAt(sb.length() - 1)) && c != symbol) {
                    sb.append(symbol);
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(toCamelCase("BBA_a"));
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(CharSequence name) {
        if (null == name || name.length() == 0) {
            return "";
        }
        char c;
        if (name.toString().contains(UNDERLINE_STR)) {
            final StringBuilder sb = new StringBuilder();
            boolean upperCase = false;
            for (int i = 0; i < name.length(); i++) {
                c = name.charAt(i);
                if (c == UNDERLINE) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return name.toString();
        }
    }

    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }
}
