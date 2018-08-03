package com.hoperun.domain;

public class ExamPaperTemPlateDetail {
    private Integer id;

    private Integer colTemplateId;

    private String colTemplateName;

    private Integer colSubjectType;

    private Integer colSubjectCount;

    private Integer colDifficuteLevel;

    private Integer colSubjectPoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColTemplateId() {
        return colTemplateId;
    }

    public void setColTemplateId(Integer colTemplateId) {
        this.colTemplateId = colTemplateId;
    }

    public String getColTemplateName() {
        return colTemplateName;
    }

    public void setColTemplateName(String colTemplateName) {
        this.colTemplateName = colTemplateName == null ? null : colTemplateName.trim();
    }

    public Integer getColSubjectType() {
        return colSubjectType;
    }

    public void setColSubjectType(Integer colSubjectType) {
        this.colSubjectType = colSubjectType;
    }

    public Integer getColSubjectCount() {
        return colSubjectCount;
    }

    public void setColSubjectCount(Integer colSubjectCount) {
        this.colSubjectCount = colSubjectCount;
    }

    public Integer getColDifficuteLevel() {
        return colDifficuteLevel;
    }

    public void setColDifficuteLevel(Integer colDifficuteLevel) {
        this.colDifficuteLevel = colDifficuteLevel;
    }

    public Integer getColSubjectPoint() {
        return colSubjectPoint;
    }

    public void setColSubjectPoint(Integer colSubjectPoint) {
        this.colSubjectPoint = colSubjectPoint;
    }
}