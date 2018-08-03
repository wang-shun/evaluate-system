package com.hoperun.entity;

public class SubjectDb {
    private Integer id;

    private String subjectDbName;

    private String subjectDbDesc;

    private Integer isOpen;

    private Long createTime;

    private Long updateTime;

    private String creatorId;

    private String updaterId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getSubjectDbName() {
		return subjectDbName;
	}

	public void setSubjectDbName(String subjectDbName) {
		this.subjectDbName = subjectDbName;
	}

	public String getSubjectDbDesc() {
		return subjectDbDesc;
	}

	public void setSubjectDbDesc(String subjectDbDesc) {
		this.subjectDbDesc = subjectDbDesc;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

	
  
}