package com.hoperun.papermodle;

import java.util.List;

public class Subject {
    private Integer id;
    private String subjectContent;
    private List<SubjectItem> subjectItems;
    
    
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

    public List<SubjectItem> getSubjectItems()
    {
        return subjectItems;
    }

    public void setSubjectItems(List<SubjectItem> subjectItems)
    {
        this.subjectItems = subjectItems;
    }

 
  

	
 
}