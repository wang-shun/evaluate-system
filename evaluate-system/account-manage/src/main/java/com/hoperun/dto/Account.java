package com.hoperun.dto;

public class Account {
    private String id;

    private String colAccountName;

    private String colPassowrd;

    private Integer colRoleId;

    private String colFullName;

    private String colMobile;

    private String colEmail;

    private Integer colDepartmentId;

    private String colLevel;

    private Long colCreateTime;

    private Long colUpdateTime;

    private String colCreatorId;

    private String colUpdatorId;

    private Long colLoginTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getColAccountName() {
        return colAccountName;
    }

    public void setColAccountName(String colAccountName) {
        this.colAccountName = colAccountName == null ? null : colAccountName.trim();
    }

    public String getColPassowrd() {
        return colPassowrd;
    }

    public void setColPassowrd(String colPassowrd) {
        this.colPassowrd = colPassowrd == null ? null : colPassowrd.trim();
    }

    public Integer getColRoleId() {
        return colRoleId;
    }

    public void setColRoleId(Integer colRoleId) {
        this.colRoleId = colRoleId;
    }

    public String getColFullName() {
        return colFullName;
    }

    public void setColFullName(String colFullName) {
        this.colFullName = colFullName == null ? null : colFullName.trim();
    }

    public String getColMobile() {
        return colMobile;
    }

    public void setColMobile(String colMobile) {
        this.colMobile = colMobile == null ? null : colMobile.trim();
    }

    public String getColEmail() {
        return colEmail;
    }

    public void setColEmail(String colEmail) {
        this.colEmail = colEmail == null ? null : colEmail.trim();
    }

    public Integer getColDepartmentId() {
        return colDepartmentId;
    }

    public void setColDepartmentId(Integer colDepartmentId) {
        this.colDepartmentId = colDepartmentId;
    }

    public String getColLevel() {
        return colLevel;
    }

    public void setColLevel(String colLevel) {
        this.colLevel = colLevel == null ? null : colLevel.trim();
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

    public String getColUpdatorId() {
        return colUpdatorId;
    }

    public void setColUpdatorId(String colUpdatorId) {
        this.colUpdatorId = colUpdatorId == null ? null : colUpdatorId.trim();
    }

    public Long getColLoginTime() {
        return colLoginTime;
    }

    public void setColLoginTime(Long colLoginTime) {
        this.colLoginTime = colLoginTime;
    }
}