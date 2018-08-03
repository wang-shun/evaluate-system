package com.hoperun.entity;

import java.io.Serializable;

public class SubjectItem implements Serializable{

	/**
     * 注释内容
     */
    private static final long serialVersionUID = -8403880882372200730L;

    private Integer id;

    private Integer subjectId;

    private String optionContent;

    private Integer optionOrder;

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

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

	public Integer getOptionOrder() {
		return optionOrder;
	}

	public void setOptionOrder(Integer optionOrder) {
		this.optionOrder = optionOrder;
	}

   
}