package com.hoperun.modle;
/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public class ExamStruct {
	private Integer id;

	private Integer colExamId;

	private Integer colSubjectType;

	private Integer colSubjectCount;

	private Integer colSubjectPoint;
	private Integer colDifficultLevel;

	public Integer getColDifficultLevel() {
		return colDifficultLevel;
	}

	public void setColDifficultLevel(Integer colDifficultLevel) {
		this.colDifficultLevel = colDifficultLevel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getColExamId() {
		return colExamId;
	}

	public void setColExamId(Integer colExamId) {
		this.colExamId = colExamId;
	}

	public Integer getColSubjectType() {
		return colSubjectType;
	}

	public void setColSubjectType(Integer colSubjectType) {
		this.colSubjectType = colSubjectType;
	}

	public Integer getColSubjectCount() {
		return colSubjectCount;
	}

	public void setColSubjectCount(Integer colSubjectCount) {
		this.colSubjectCount = colSubjectCount;
	}

	public Integer getColSubjectPoint() {
		return colSubjectPoint;
	}

	public void setColSubjectPoint(Integer colSubjectPoint) {
		this.colSubjectPoint = colSubjectPoint;
	}
}