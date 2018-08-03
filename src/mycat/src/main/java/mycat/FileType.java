package mycat;
/**
 * 统计分析文件类型
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月31日]
 * @since  [云服务/模块版本]
 */
public enum FileType
{
    java("java"), xml("xml"), yml("yml"), sql("sql"), vue("vue");
    
    private String type;
    
    FileType(String type)
    {
        this.type = type;
    }
    
    public static boolean isContain(String type)
    {
        if(java.type.equals(type) || xml.type.equals(type) || yml.type.equals(type) || sql.type.equals(type))
        {
            return true;
        }
        if(vue.type.equals(type))
        {
            return true;
        }
        return false;
    }
    
    public static boolean isContain(String type, String[] fileType)
    {
        if(null != fileType)
        {
            for(String one : fileType)
            {
                if(one.equals(type))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
