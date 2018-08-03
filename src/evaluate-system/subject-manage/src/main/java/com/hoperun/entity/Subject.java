package com.hoperun.entity;

import java.util.List;

public class Subject {
    private Integer id;

    private String subjectContent;

    private Integer subjectDbId;
    
    private String subjectDbName;

    private Integer subjectType;

    private Integer defficultyWeight;

    private Integer isOpen;

    private Long createTime;

    private Long updateTime;

    private String creatorId;

    private String updaterId;

    private List<SubjectItem>items;
    
    private SubjectAnswer answer;
    
    
    public SubjectAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(SubjectAnswer answer) {
        this.answer = answer;
    }

    public List<SubjectItem> getItems() {
        return items;
    }

    public void setItems(List<SubjectItem> items) {
        this.items = items;
    }

    public String getSubjectName() {
		return subjectDbName;
	}

	public void setSubjectName(String subjectDbName) {
		this.subjectDbName = subjectDbName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getSubjectContent() {
		return subjectContent;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}

	public Integer getSubjectDbId() {
		return subjectDbId;
	}

	public void setSubjectDbId(Integer subjectDbId) {
		this.subjectDbId = subjectDbId;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public Integer getDefficultyWeight() {
		return defficultyWeight;
	}

	public void setDefficultyWeight(Integer defficultyWeight) {
		this.defficultyWeight = defficultyWeight;
	}

	

	public Integer getIsOpen()
    {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen)
    {
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


	public String getSubjectDbName() {
        return subjectDbName;
    }

    public void setSubjectDbName(String subjectDbName) {
        this.subjectDbName = subjectDbName;
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