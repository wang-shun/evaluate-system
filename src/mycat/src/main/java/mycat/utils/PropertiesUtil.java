package mycat.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件工具
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月2日]
 * @since  [云服务/模块版本]
 */
public class PropertiesUtil
{
    
    public static Map<String, String> get(String filePath)
    {
        Map<String, String> out = new HashMap<String, String>();
        Properties prop = new Properties();
        InputStream in = null;
        try
        {
            
            //读取属性文件properties
            in = new BufferedInputStream(new FileInputStream(filePath));
            
            //加载属性列表
            prop.load(in);
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext())
            {
                String key = it.next();
                System.out.println(key + ":" + prop.getProperty(key));
                out.put(key, prop.getProperty(key));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return out;
    }
}
