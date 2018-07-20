package com.hoperun.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoperun.domain.Exam;
import com.hoperun.domain.ExamAccount;
import com.hoperun.domain.ExamStruct;
import com.hoperun.mapper.ExamMapper;
import com.hoperun.mapper.ExamStructMapper;

@Service
public class ExamService
{
    private static Logger logger = LoggerFactory.getLogger(ExamService.class);
    
    @Autowired
    ExamMapper examMapper;
    
    @Autowired
    ExamStructMapper examStructMapper;
    
    @Autowired
    ExamAccountService examAccountService;
    
    public boolean insertExamPojo(Exam exam)
    {
        try
        {
            examMapper.insertSelective(exam);
        }
        catch(Exception e)
        {
            logger.error("to insert exam pojo error");
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout=36000, rollbackFor=Exception.class)
    public boolean createExamination(Map<String, Object> obj)
    {
        Exam examBean = (Exam)obj.get("examBean");
        List<ExamStruct> examStruct = (List<ExamStruct>)obj.get("examStruct");
        List<ExamAccount> examAccountList =  (List<ExamAccount>)obj.get("examAccountList");
        
        int examId = 0;
        //先录入exam
        try
        {
            examMapper.insertSelective(examBean);
        }
        catch(Exception e)
        {
            logger.error("to insert exam pojo error {}", e);
            logger.error(e.getMessage());
            return false;
        }
        List<Exam> data = examMapper.selectCreateExamid(examBean);
        if(data != null && data.size() > 0)
        {
            examId = data.get(0).getId();
        }
        
        if(examId == 0)
        {
            logger.error("to insert exam pojo error");
            return false;
        }
        
        //再录入examstruct
        try
        {
            for(ExamStruct one : examStruct)
            {
                one.setColExamId(examId);
                examStructMapper.insertSelective(one);
            }
        }
        catch(Exception e)
        {
            logger.error("to insert exam struct pojo error {}", e);
            return false;
        }
        
        //最后录入关联关系
        for(ExamAccount one : examAccountList)
        {
            one.setColExamId(examId);
        }
        examAccountService.addExamAccountList(examAccountList);
        return true;
    }
    
    public Map<String, Object> getExamPojo(int examId)
    {
        Map<String, Object> out = null;
        
        try{
            Exam data = examMapper.selectByPrimaryKey(examId);
            if(data != null)
            {
                out = new HashMap<String, Object>();
                out.put("infos", data);
            }
        }
        catch(Exception e)
        {
            logger.error("to select exam pojo by examid error");
        }
        
        return out;
    }
    
    
    public Map<String, Object> getAllExamPojo()
    {
        Map<String, Object> out = null;
        
        try{
            List<Exam> data = examMapper.selectAll();
            if(data != null)
            {
                out = new HashMap<String, Object>();
                out.put("infos", data);
            }
        }
        catch(Exception e)
        {
            logger.error("to select all exam pojo error");
        }
        
        return out;
    }
    
}
