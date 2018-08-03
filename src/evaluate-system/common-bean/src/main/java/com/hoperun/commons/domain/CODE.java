package com.hoperun.commons.domain;
public enum CODE
{
    RESP_SUC("0000", "success"), UNEXP_ERR("4444", "error happend"), FIRST_LOGIN("1111", "first login");
    
    String code;
    
    String message;
    
    CODE(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
