package com.hoperun.entity;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public class ExamPaper {
    private Integer id;

    private String paperName;

    private String paperOwnerId;

    private String examId;
    private Integer paperId;

    private Integer score;

    private Integer paperStatus;

    private String paperStatusDesc;

    private Long startTime;

    private Long endTime;

    private Long submitTime;

    private String ipaddr;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperOwnerId() {
        return paperOwnerId;
    }

    public void setPaperOwnerId(String paperOwnerId) {
        this.paperOwnerId = paperOwnerId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(Integer paperStatus) {
        this.paperStatus = paperStatus;
    }

    public String getPaperStatusDesc() {
        return paperStatusDesc;
    }

    public void setPaperStatusDesc(String paperStatusDesc) {
        this.paperStatusDesc = paperStatusDesc;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Long submitTime) {
        this.submitTime = submitTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

}