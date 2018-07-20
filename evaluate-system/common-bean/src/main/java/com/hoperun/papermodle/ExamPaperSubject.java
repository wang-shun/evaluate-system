package com.hoperun.papermodle;

public class ExamPaperSubject {
	private Integer paperId;
	private ExamSubject[] singleSubjects;
	private ExamSubject[] multiSubjects;

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public ExamSubject[] getSingleSubjects() {
		return singleSubjects;
	}

	public void setSingleSubjects(ExamSubject[] singleSubjects) {
		this.singleSubjects = singleSubjects;
	}

	public ExamSubject[] getMultiSubjects() {
		return multiSubjects;
	}

	public void setMultiSubjects(ExamSubject[] multiSubjects) {
		this.multiSubjects = multiSubjects;
	}

}