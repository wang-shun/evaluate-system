/*
 * 文 件 名:  ExamPaperEntity.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月17日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.modle;

import java.util.List;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月17日]
 * @since  [云服务/模块版本]
 */
public class ExamPaperEntity
{
    private Integer id;
    
    private Integer paperId;
    
    private String paperName;
    
    private String paperOwnerId;
    
    private String examId;
    
    private Integer score;
    
    private Integer paperStatus;
    
    private String paperStatusDesc;
    
    private Long startTime;
    
    private Long endTime;
    
    private Long submitTime;
    
    private String ipaddr;
    private String subjectDbId;
    
    
    List<ExamStruct> examStructs;
    
    
    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getSubjectDbId()
    {
        return subjectDbId;
    }

    public void setSubjectDbId(String subjectDbId)
    {
        this.subjectDbId = subjectDbId;
    }

    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getPaperName()
    {
        return paperName;
    }
    
    public void setPaperName(String paperName)
    {
        this.paperName = paperName;
    }
    
    public String getPaperOwnerId()
    {
        return paperOwnerId;
    }
    
    public void setPaperOwnerId(String paperOwnerId)
    {
        this.paperOwnerId = paperOwnerId;
    }
    
    public String getExamId()
    {
        return examId;
    }
    
    public void setExamId(String examId)
    {
        this.examId = examId;
    }
    
    public Integer getScore()
    {
        return score;
    }
    
    public void setScore(Integer score)
    {
        this.score = score;
    }
    
    public Integer getPaperStatus()
    {
        return paperStatus;
    }
    
    public void setPaperStatus(Integer paperStatus)
    {
        this.paperStatus = paperStatus;
    }
    
    public String getPaperStatusDesc()
    {
        return paperStatusDesc;
    }
    
    public void setPaperStatusDesc(String paperStatusDesc)
    {
        this.paperStatusDesc = paperStatusDesc;
    }
    
    public Long getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Long startTime)
    {
        this.startTime = startTime;
    }
    
    public Long getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Long endTime)
    {
        this.endTime = endTime;
    }
    
    public Long getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Long submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public String getIpaddr()
    {
        return ipaddr;
    }
    
    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }
    
    public List<ExamStruct> getExamStructs()
    {
        return examStructs;
    }
    
    public void setExamStructs(List<ExamStruct> examStructs)
    {
        this.examStructs = examStructs;
    }
    
}
