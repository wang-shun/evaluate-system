package com.hoperun.papermodle;

import java.io.Serializable;

public class SubjectAnswer implements Serializable{

	/**
     * 注释内容
     */
    private static final long serialVersionUID = -5816218401508172023L;

    private Integer id;

    private Integer subjectId;

    private Integer submitAnswer;
    private Integer subjectAnswer;

    public Integer getSubjectAnswer()
    {
        return subjectAnswer;
    }

    public void setSubjectAnswer(Integer subjectAnswer)
    {
        this.subjectAnswer = subjectAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

    public Integer getSubmitAnswer()
    {
        return submitAnswer;
    }

    public void setSubmitAnswer(Integer submitAnswer)
    {
        this.submitAnswer = submitAnswer;
    }

	

  
}