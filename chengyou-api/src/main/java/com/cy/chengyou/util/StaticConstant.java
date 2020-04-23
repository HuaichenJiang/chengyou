package com.cy.chengyou.util;

import java.util.regex.Pattern;

public class StaticConstant {

    // 字符集
    public static String ENCODING_UTF_8 = "UTF-8";
    public static String ENCODING_GBK = "GBK";
    public static String ENCODING_GB2312 = "gb-2312";

    // 请求方式
    public static String POST = "POST";
    public static String GET = "GET";

    // 请求头
    public static final String AUTHORIZATION = "Authorization";
    public static final String JSON_TYPE = "application/json";
    public static final String FORM_DATA = "application/form-data";
    public static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String TEXT_XML = "text/xml";
    public static final String APPLICATION_XML = "application/xml";
    private static final Pattern HOST_PATTERN = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");

}
