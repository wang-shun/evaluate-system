package mycat.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        InputStreamReader reader = null;
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(filePath);
            reader = new InputStreamReader(in, "UTF-8");
            prop.load(reader);
            
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
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
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
