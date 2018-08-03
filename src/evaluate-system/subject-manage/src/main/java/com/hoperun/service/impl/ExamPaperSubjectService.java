/*
 * 文 件 名:  ExamPaperSubjectService.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月17日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.dao.SubjectAnswerMapper;
import com.hoperun.dao.SubjectItemMapper;
import com.hoperun.dao.SubjectMapper;
import com.hoperun.entity.Subject;
import com.hoperun.entity.SubjectAnswer;
import com.hoperun.papermodle.ExamPaperSubject;
import com.hoperun.papermodle.ExamSubject;

import com.hoperun.papermodle.SubjectItem;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月17日]
 * @since  [云服务/模块版本]
 */
@Service("examPaperSubjectService")
public class ExamPaperSubjectService
{
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private SubjectItemMapper subjectItemMapper;
    @Autowired
    private SubjectAnswerMapper subjectAnswerMapper;
    //根据试题类型和试题总数组装试卷
    public  List<ExamSubject> acessmble(String subjectType,int count,int subjectDbId,int defficultyWeight) {
      
        List<Subject> subjects=subjectMapper.querySubject(count, subjectDbId, Integer.valueOf(subjectType),defficultyWeight);
        List<ExamSubject> list=new ArrayList<>();
        for (Subject subject : subjects)
        {
            ExamSubject examsubject=new ExamSubject();
            examsubject.setId(subject.getId());
            examsubject.setSubjectContent(subject.getSubjectContent());
            //根据题目id查询题目详情
            List<SubjectItem> items= subjectItemMapper.selectBySubjectId(subject.getId());
            examsubject.setSubjectItems(items);
            list.add(examsubject);
            //根据题目id查询答案
            SubjectAnswer answer=  subjectAnswerMapper.queryBySubjectId(subject.getId());
            com.hoperun.papermodle.SubjectAnswer subjectAnswer=this.acessmeAnswer(answer);
            examsubject.setAnswer(subjectAnswer);
        }
        
        return list;
        
    }
    public com.hoperun.papermodle.SubjectAnswer acessmeAnswer(SubjectAnswer answer){
        com.hoperun.papermodle.SubjectAnswer subjectAnswer=new com.hoperun.papermodle.SubjectAnswer();
        subjectAnswer.setId(answer.getId());
        subjectAnswer.setSubjectId(answer.getSubjectId());
        subjectAnswer.setSubjectAnswer(answer.getSubjectAnswer());
        return subjectAnswer;
        
    }
}
