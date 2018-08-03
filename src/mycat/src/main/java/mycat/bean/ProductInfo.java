package mycat.bean;

import java.util.Date;
/**
 * 对应表 product_info_tb
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月1日]
 * @since  [云服务/模块版本]
 */
public class ProductInfo {
    private Integer id;

    private String productid;

    private String productname;

    private Date endtime;
    
    private Date begintime;

    private String branchpath;

    public Date getBegintime()
    {
        return begintime;
    }

    public void setBegintime(Date begintime)
    {
        this.begintime = begintime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid == null ? null : productid.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getBranchpath() {
        return branchpath;
    }

    public void setBranchpath(String branchpath) {
        this.branchpath = branchpath == null ? null : branchpath.trim();
    }
}