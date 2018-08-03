package com.hoperun.domain;

public class ExamAccount {
    private Integer id;

    private Integer colExamId;

    private String colAccountId;

    private Integer colParerCount;

    private Integer colLimitCount;

    private Integer colIsPass;

    private String colCreatorId;

    private Long colCreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColExamId() {
        return colExamId;
    }

    public void setColExamId(Integer colExamId) {
        this.colExamId = colExamId;
    }

    public String getColAccountId() {
        return colAccountId;
    }

    public void setColAccountId(String colAccountId) {
        this.colAccountId = colAccountId == null ? null : colAccountId.trim();
    }

    public Integer getColParerCount() {
        return colParerCount;
    }

    public void setColParerCount(Integer colParerCount) {
        this.colParerCount = colParerCount;
    }

    public Integer getColLimitCount() {
        return colLimitCount;
    }

    public void setColLimitCount(Integer colLimitCount) {
        this.colLimitCount = colLimitCount;
    }

    public Integer getColIsPass() {
        return colIsPass;
    }

    public void setColIsPass(Integer colIsPass) {
        this.colIsPass = colIsPass;
    }

    public String getColCreatorId() {
        return colCreatorId;
    }

    public void setColCreatorId(String colCreatorId) {
        this.colCreatorId = colCreatorId;
    }

    public Long getColCreateTime() {
        return colCreateTime;
    }

    public void setColCreateTime(Long colCreateTime) {
        this.colCreateTime = colCreateTime;
    }
}