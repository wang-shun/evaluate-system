package com.hoperun.domain;

public class Exam {
    private Integer id;

    private String colExamName;

    private String colExamDesc;

    private Integer colSubjectDbId;

    private Long colOpenTime;

    private Long colCloseTime;

    private Integer colTotalScore;

    private Integer colPassScore;

    private Integer colDuration;

    private Integer colLimitCount;

    private Integer colPublish;

    private Long colCreateTime;

    private Long colUpdateTime;

    private String colCreatorId;

    private String colUpdateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColExamName() {
        return colExamName;
    }

    public void setColExamName(String colExamName) {
        this.colExamName = colExamName == null ? null : colExamName.trim();
    }

    public String getColExamDesc() {
        return colExamDesc;
    }

    public void setColExamDesc(String colExamDesc) {
        this.colExamDesc = colExamDesc == null ? null : colExamDesc.trim();
    }

    public Integer getColSubjectDbId() {
        return colSubjectDbId;
    }

    public void setColSubjectDbId(Integer colSubjectDbId) {
        this.colSubjectDbId = colSubjectDbId;
    }

    public Long getColOpenTime() {
        return colOpenTime;
    }

    public void setColOpenTime(Long colOpenTime) {
        this.colOpenTime = colOpenTime;
    }

    public Long getColCloseTime() {
        return colCloseTime;
    }

    public void setColCloseTime(Long colCloseTime) {
        this.colCloseTime = colCloseTime;
    }

    public Integer getColTotalScore() {
        return colTotalScore;
    }

    public void setColTotalScore(Integer colTotalScore) {
        this.colTotalScore = colTotalScore;
    }

    public Integer getColPassScore() {
        return colPassScore;
    }

    public void setColPassScore(Integer colPassScore) {
        this.colPassScore = colPassScore;
    }

    public Integer getColDuration() {
        return colDuration;
    }

    public void setColDuration(Integer colDuration) {
        this.colDuration = colDuration;
    }

    public Integer getColLimitCount() {
        return colLimitCount;
    }

    public void setColLimitCount(Integer colLimitCount) {
        this.colLimitCount = colLimitCount;
    }

    public Integer getColPublish() {
        return colPublish;
    }

    public void setColPublish(Integer colPublish) {
        this.colPublish = colPublish;
    }

    public Long getColCreateTime() {
        return colCreateTime;
    }

    public void setColCreateTime(Long colCreateTime) {
        this.colCreateTime = colCreateTime;
    }

    public Long getColUpdateTime() {
        return colUpdateTime;
    }

    public void setColUpdateTime(Long colUpdateTime) {
        this.colUpdateTime = colUpdateTime;
    }

    public String getColCreatorId() {
        return colCreatorId;
    }

    public void setColCreatorId(String colCreatorId) {
        this.colCreatorId = colCreatorId == null ? null : colCreatorId.trim();
    }

    public String getColUpdateId() {
        return colUpdateId;
    }

    public void setColUpdateId(String colUpdateId) {
        this.colUpdateId = colUpdateId == null ? null : colUpdateId.trim();
    }
}