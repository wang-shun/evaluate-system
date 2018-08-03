package com.hoperun.excelmodel;

import java.io.Serializable;
import java.util.List;

public class ExcelSubject implements Serializable {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2190229559927857167L;

	private String content;

	private List<String> answerRadio;

	private List<String> answer;

	private String[] rightAnswer;

	private int subjectType;

	private int isUse;

	private int diffcutType;

	private int subjectDbId;

	private int index;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getAnswerRadio() {
		return answerRadio;
	}

	public void setAnswerRadio(List<String> answerRadio) {
		this.answerRadio = answerRadio;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	

	public String[] getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String[] rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public int getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public int getDiffcutType() {
		return diffcutType;
	}

	public void setDiffcutType(int diffcutType) {
		this.diffcutType = diffcutType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSubjectDbId() {
		return subjectDbId;
	}

	public void setSubjectDbId(int subjectDbId) {
		this.subjectDbId = subjectDbId;
	}

	@Override
	public String toString() {
		return "ExcelSubject [content=" + content + ", answerRadio=" + answerRadio + ", answer=" + answer
				+ ", rightAnswer=" + rightAnswer + ", subjectType=" + subjectType + ", isUse=" + isUse
				+ ", diffcutType=" + diffcutType + "]";
	}

}
