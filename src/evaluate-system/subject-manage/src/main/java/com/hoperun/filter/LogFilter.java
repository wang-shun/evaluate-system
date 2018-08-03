 package com.hoperun.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author mWX576942
 * @date 2018/07/26
 */
public class LogFilter extends Filter<ILoggingEvent>{

private static final String INTERFACE_LOG_NAME = "interfaceLog";  
    
    @Override
    public FilterReply decide(ILoggingEvent arg0)
    {
        
        if (arg0.getLoggerName().contains(INTERFACE_LOG_NAME))
        {
            return FilterReply.ACCEPT;
        }      
        
        return FilterReply.DENY;
    }
}
