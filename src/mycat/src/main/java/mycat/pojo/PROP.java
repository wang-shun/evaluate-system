package mycat.pojo;
/**
 * 配置文件key
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月2日]
 * @since  [云服务/模块版本]
 */
public enum PROP
{
    cct_report_xls("cct_report_xls"), mycat_report_xls_path("mycat_report_xls_path"), commit_log_path("commit_log_path")
    , product_name("product_name"), product_id("product_id"), branch_path("branch_path")
    , analysis_begin_time("analysis_begin_time"), analysis_end_time("analysis_end_time"), replace_str("replace_str")
    , support_file_type("support_file_type");
    
    private String key;
    
    PROP(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
    
}
