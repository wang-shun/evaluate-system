 package com.hoperun.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**接口日志
 * @author mWX576942
 * @date 2018/07/26
 */
public class InterfaceLog {
    private static Logger interfaceLog = LoggerFactory.getLogger("interfaceLog");
    
    public static void interfaceInfoLog(String msg)
    {
              
        interfaceLog.info(msg);
    }
    
    public static void interfaceErrorLog(String msg)
    {
        
        interfaceLog.error(msg);
    }

}
