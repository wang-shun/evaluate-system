package com.hoperun.dto;

import java.util.Date;

public class Department {
    private Integer id;

    private String colName;

    private String colDesc;

    private Integer colParentId;

    private Date colCreateTime;

    private String colCreateorId;

    private String colUpdaterId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName == null ? null : colName.trim();
    }

    public String getColDesc() {
        return colDesc;
    }

    public void setColDesc(String colDesc) {
        this.colDesc = colDesc == null ? null : colDesc.trim();
    }

    public Integer getColParentId() {
        return colParentId;
    }

    public void setColParentId(Integer colParentId) {
        this.colParentId = colParentId;
    }

    public Date getColCreateTime() {
        return colCreateTime;
    }

    public void setColCreateTime(Date colCreateTime) {
        this.colCreateTime = colCreateTime;
    }

    public String getColCreateorId() {
        return colCreateorId;
    }

    public void setColCreateorId(String colCreateorId) {
        this.colCreateorId = colCreateorId == null ? null : colCreateorId.trim();
    }

    public String getColUpdaterId() {
        return colUpdaterId;
    }

    public void setColUpdaterId(String colUpdaterId) {
        this.colUpdaterId = colUpdaterId == null ? null : colUpdaterId.trim();
    }
}