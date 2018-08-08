package mycat.bean;

public class SingleFileTotle {
    private Integer id;

    private String filepath;

    private Integer totleaddline;

    private Integer totledeleteline;
    
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

    public Integer getTotleaddline() {
        return totleaddline;
    }

    public void setTotleaddline(Integer totleaddline) {
        this.totleaddline = totleaddline;
    }

    public Integer getTotledeleteline() {
        return totledeleteline;
    }

    public void setTotledeleteline(Integer totledeleteline) {
        this.totledeleteline = totledeleteline;
    }
}