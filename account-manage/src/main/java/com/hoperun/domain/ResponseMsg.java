package com.hoperun.domain;

import java.util.Map;
/**
 * 基础响应结构体
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月12日]
 * @since  [云服务/模块版本]
 */
public class ResponseMsg
{
    private String code;
    
    private String message;
    
    private Map<String, Object> data;
    
    public ResponseMsg()
    {
        
    }
    
    public ResponseMsg(CODE code)
    {
        this.code = code.code;
        this.message = code.message;
    }

    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public Map<String, Object> getData()
    {
        return data;
    }
    
    public void setData(Map<String, Object> data)
    {
        this.data = data;
    }
    
}

