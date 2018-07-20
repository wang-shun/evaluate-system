/*
 * 文 件 名:  SubjectSubmitModle.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月18日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.modle;

import java.util.List;

import com.hoperun.papermodle.SubjectAnswer;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月18日]
 * @since  [云服务/模块版本]
 */
public class SubjectSubmitModle
{
    //试卷id
    private int examPaperId;
    //考生id
    private String ownerId;
    //开始时间
    private Long beginTime;
    //结束时间
    private Long endTime;
    //提交答案
    private List<SubjectAnswer>answers;
    public int getExamPaperId()
    {
        return examPaperId;
    }
    public void setExamPaperId(int examPaperId)
    {
        this.examPaperId = examPaperId;
    }
    public String getOwnerId()
    {
        return ownerId;
    }
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    public Long getBeginTime()
    {
        return beginTime;
    }
    public void setBeginTime(Long beginTime)
    {
        this.beginTime = beginTime;
    }
    public Long getEndTime()
    {
        return endTime;
    }
    public void setEndTime(Long endTime)
    {
        this.endTime = endTime;
    }
    public List<SubjectAnswer> getAnswers()
    {
        return answers;
    }
    public void setAnswers(List<SubjectAnswer> answers)
    {
        this.answers = answers;
    }
    
    
}
