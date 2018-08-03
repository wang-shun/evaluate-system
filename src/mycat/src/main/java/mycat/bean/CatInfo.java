package mycat.bean;
import java.util.Date;
/**
 * 对应表 cat_info_tb
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月31日]
 * @since  [云服务/模块版本]
 */
public class CatInfo {
    private String productid;

    private String authorid;

    private Date committime;

    private String filepath;

    private Integer addline;

    private Integer deleteline;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid == null ? null : productid.trim();
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid == null ? null : authorid.trim();
    }

    public Date getCommittime() {
        return committime;
    }

    public void setCommittime(Date committime) {
        this.committime = committime;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public Integer getAddline() {
        return addline;
    }

    public void setAddline(Integer addline) {
        this.addline = addline;
    }

    public Integer getDeleteline() {
        return deleteline;
    }

    public void setDeleteline(Integer deleteline) {
        this.deleteline = deleteline;
    }
}