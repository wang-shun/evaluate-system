package mycat.bean;

public class CctInfo {
    private Integer id;

    private String filepath;

    private Integer addline;

    private Integer deleteline;
    
    private Integer modifyline;
    
    private String filetype;

    public String getFiletype()
    {
        return filetype;
    }

    public void setFiletype(String filetype)
    {
        this.filetype = filetype;
    }

    public Integer getModifyline()
    {
        return modifyline;
    }

    public void setModifyline(Integer modifyline)
    {
        this.modifyline = modifyline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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