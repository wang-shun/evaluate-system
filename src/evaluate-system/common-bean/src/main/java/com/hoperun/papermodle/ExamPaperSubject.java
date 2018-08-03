package com.hoperun.papermodle;

import java.util.Arrays;
import java.util.List;

public class ExamPaperSubject {
	private Integer paperId;

	private List<ExamSubject> singleSubjects;
	private List<ExamSubject> multiSubjects;

	

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public List<ExamSubject> getSingleSubjects() {
		return singleSubjects;
	}

	public void setSingleSubjects(List<ExamSubject> singleSubjects) {
		this.singleSubjects = singleSubjects;
	}

	public List<ExamSubject> getMultiSubjects() {
		return multiSubjects;
	}

	public void setMultiSubjects(List<ExamSubject> multiSubjects) {
		this.multiSubjects = multiSubjects;
	}

	


}