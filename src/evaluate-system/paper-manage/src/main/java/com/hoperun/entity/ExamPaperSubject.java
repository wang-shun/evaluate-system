package com.hoperun.entity;
/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public class ExamPaperSubject {
    private Integer id;

    private Integer examPaper;

    private Integer subjectId;

    private Integer subjectType;

    private Integer point;

    private Integer subjectAnswer;

    private Integer submitAnswer;

    private Integer isCorrect;

    private String subjectDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public Integer getExamPaper()
    {
        return examPaper;
    }

    public void setExamPaper(Integer examPaper)
    {
        this.examPaper = examPaper;
    }

    public Integer getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId)
    {
        this.subjectId = subjectId;
    }

    public Integer getSubjectType()
    {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType)
    {
        this.subjectType = subjectType;
    }

    public Integer getPoint()
    {
        return point;
    }

    public void setPoint(Integer point)
    {
        this.point = point;
    }

    public Integer getSubjectAnswer()
    {
        return subjectAnswer;
    }

    public void setSubjectAnswer(Integer subjectAnswer)
    {
        this.subjectAnswer = subjectAnswer;
    }

    public Integer getSubmitAnswer()
    {
        return submitAnswer;
    }

    public void setSubmitAnswer(Integer submitAnswer)
    {
        this.submitAnswer = submitAnswer;
    }

    public Integer getIsCorrect()
    {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect)
    {
        this.isCorrect = isCorrect;
    }

    public String getSubjectDetail()
    {
        return subjectDetail;
    }

    public void setSubjectDetail(String subjectDetail)
    {
        this.subjectDetail = subjectDetail;
    }

   
}