package com.hoperun.domain;

public class ExamPaperTemPlate {
    private Integer id;

    private String colTemplateName;

    private Integer colCreateTime;

    private String colCreator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColTemplateName() {
        return colTemplateName;
    }

    public void setColTemplateName(String colTemplateName) {
        this.colTemplateName = colTemplateName == null ? null : colTemplateName.trim();
    }

    public Integer getColCreateTime() {
        return colCreateTime;
    }

    public void setColCreateTime(Integer colCreateTime) {
        this.colCreateTime = colCreateTime;
    }

    public String getColCreator() {
        return colCreator;
    }

    public void setColCreator(String colCreator) {
        this.colCreator = colCreator == null ? null : colCreator.trim();
    }
}