package mycat.bean;

public class SingleFileAuthor {
    private Integer id;

    private String filepath;

    private String authorid;

    private Integer addline;

    private Integer deleteline;
    
    private String filetype;

    public String getFiletype()
    {
        return filetype;
    }

    public void setFiletype(String filetype)
    {
        this.filetype = filetype;
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

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid == null ? null : authorid.trim();
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