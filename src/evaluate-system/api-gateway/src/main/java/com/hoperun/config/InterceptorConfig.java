package com.hoperun.config;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hoperun.commons.domain.FinalPojo;
//import com.hoperun.service.CacheService;

@Component
public class InterceptorConfig implements HandlerInterceptor
{
//    @Autowired
//    private CacheService cacheService;
    
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
        throws Exception
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
        throws Exception
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2)
        throws Exception
    {
        // TODO Auto-generated method stub
        /**
        // 如果请求路径以accessvalidat结尾则放行，不做at校验
        
        // 否则校验at，先匹配pre at，不匹配再匹配 after at
        
        // 如果at校验不过则拦截请求并在响应请求头中加入DirectTo字段提示前台跳转登录页面
        
        // 如果at校验通过，且pre at为空则生成新at并更新值到缓存的pre at， after at并在响应请求头中加入AccessToken字段
        //，提示前台更新本地的at值用于接下来请求发送
        **/
        
        /*String url = req.getRequestURL().toString();
        String at = req.getHeader(FinalPojo.RESP_HEADER_ACCESSTOKEN);
        String userId = req.getHeader(FinalPojo.RESP_HEADER_USERID);
        if(url != null && url.endsWith(FinalPojo.SPECIAL_URL))
        {
            return true;
        }
        else
        {
            if(StringUtils.isEmpty(at) || StringUtils.isEmpty(userId))
            {
                resp.setHeader(FinalPojo.RESP_HEADER_DIRECTTO, FinalPojo.RESP_HEADER_LOGIN);
                return false;
            }
            if(at.equals(cacheService.get(userId.concat(FinalPojo.AT_KEY_PRE)))
                || at.equals(cacheService.get(userId.concat(FinalPojo.AT_KEY_AFTER))))
            {
                if(StringUtils.isEmpty(cacheService.get(userId.concat(FinalPojo.AT_KEY_PRE))))
                {
                    String orignal = UUID.randomUUID().toString().concat(String.valueOf(System.currentTimeMillis()));
                    String accessToken = DigestUtils.md5DigestAsHex(orignal.getBytes());
                    logger.info("InterceptorConfig make new at: {}", accessToken);
                    cacheService.expire(userId.concat(FinalPojo.AT_KEY_PRE), accessToken, FinalPojo.FIFTEEN_MINUTES);
                    cacheService.expire(userId.concat(FinalPojo.AT_KEY_AFTER), accessToken, FinalPojo.THIRTY_MINUTES);
                    resp.setHeader(FinalPojo.RESP_HEADER_ACCESSTOKEN, accessToken);
                }
                return true;
            }
            else
            {
                resp.setHeader(FinalPojo.RESP_HEADER_DIRECTTO, FinalPojo.RESP_HEADER_LOGIN);
                return false;
            }
        }*/
        
        return true;
    }
    
}
