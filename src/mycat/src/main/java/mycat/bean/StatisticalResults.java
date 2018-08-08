package mycat.bean;
/**
 * 个人工作量统计结果
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月2日]
 * @since  [云服务/模块版本]
 */
public class StatisticalResults
{

    private Integer id;
    
    private String authorid;
    
    private Integer addsum;
    
    private Integer deletesum;
    
    private Integer modifysum;
    
    private String filetype;

    public String getFiletype()
    {
        return filetype;
    }

    public void setFiletype(String filetype)
    {
        this.filetype = filetype;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAuthorid()
    {
        return authorid;
    }

    public void setAuthorid(String authorid)
    {
        this.authorid = authorid;
    }

    public Integer getAddsum()
    {
        return addsum;
    }

    public void setAddsum(Integer addsum)
    {
        this.addsum = addsum;
    }

    public Integer getDeletesum()
    {
        return deletesum;
    }

    public void setDeletesum(Integer deletesum)
    {
        this.deletesum = deletesum;
    }

    public Integer getModifysum()
    {
        return modifysum;
    }

    public void setModifysum(Integer modifysum)
    {
        this.modifysum = modifysum;
    }
    
}
