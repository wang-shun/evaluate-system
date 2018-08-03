package mycat.utils;
/**
 * git log处理工具
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月31日]
 * @since  [云服务/模块版本]
 */
public class GitLogUtil
{

    /**
     * 安装空格切割字符串
     * <功能详细描述>
     * @param str
     * @return
     */
    public static String[] split(String str)
    {
        String[] out = new String[3];
        StringBuilder builder = new StringBuilder();
        String temp = null;
        int index = 0;
        for(int i=0,h=0; i<str.length(); i++)
        {
            if(32 == (int)(str.charAt(i)))
            {
//                System.out.println("["+str.charAt(i)+"]");
                temp = builder.toString();
                if(!"".equals(temp))
                {
                    out[h] = temp;
                    h++;
                    builder.delete(0, builder.length());
                }
                continue;
            }
            if(h == 2)
            {
                index = i;
                break;
            }
            builder.append(str.charAt(i));
        }
        out[2] = str.substring(index, str.length());
        return out;
    }
    
    /**
     * 取后缀
     * <功能详细描述>
     * @param str
     * @return
     */
    public static String suffix(String str)
    {
        String out = null;
        if(null != str)
        {
            out = str.substring(str.lastIndexOf(".")+1, str.length());
        }
        return out;
    }
}
