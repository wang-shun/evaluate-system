package com.hoperun.commons.domain;

public final class FinalPojo
{

    /** 账户初始密码**/
    public static final String INIT_PASSWORD = "123456";
    
    /** at前置缓存key标识**/
    public static final String AT_KEY_PRE = "pre";
    
    /** at后置缓存key标识**/
    public static final String AT_KEY_AFTER = "after";
    
    /** 15分钟的毫秒数**/
    public static final long FIFTEEN_MINUTES = 15*60*1000l;
    
    /** 30分钟的毫秒数**/
    public static final long THIRTY_MINUTES = 30*60*1000l;
    
    /** 请求响应头中的at字段名**/
    public static final String RESP_HEADER_ACCESSTOKEN = "AccessToken";
    
    /** 请求响应头中的userid字段名**/
    public static final String RESP_HEADER_USERID = "UserId";
    
    /** 请求响应头中的DirectTo字段名**/
    public static final String RESP_HEADER_DIRECTTO = "DirectTo";
    
    /** 请求响应头中的login返回值**/
    public static final String RESP_HEADER_LOGIN = "login";
    
    /** 请求校验的url路径后缀**/
    public static final String SPECIAL_URL = "accessvalidat";
}
