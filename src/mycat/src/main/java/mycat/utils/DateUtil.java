package mycat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 时间工具
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月31日]
 * @since  [云服务/模块版本]
 */
public class DateUtil
{
    
    /**
     * 字符串转date
     * <功能详细描述>
     * @param str
     * @return
     */
    public static Date strToDate(String str)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        //必须捕获异常
        try
        {
            date = simpleDateFormat.parse(str);
        }
        catch (ParseException px)
        {
            px.printStackTrace();
        }
        return date;
    }
}
