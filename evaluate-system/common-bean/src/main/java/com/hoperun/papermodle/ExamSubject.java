package com.hoperun.papermodle;

import java.io.Serializable;
import java.util.List;

public class ExamSubject implements Serializable{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 4837392027771466722L;
    private Integer id;
    private String subjectContent;
    private List<SubjectItem> subjectItems;
    private SubjectAnswer answer;
    
    public SubjectAnswer getAnswer()
    {
        return answer;
    }

    public void setAnswer(SubjectAnswer answer)
    {
        this.answer = answer;
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

    public List<SubjectItem> getSubjectItems()
    {
        return subjectItems;
    }

    public void setSubjectItems(List<SubjectItem> subjectItems)
    {
        this.subjectItems = subjectItems;
    }

   

 
  

	
 
}