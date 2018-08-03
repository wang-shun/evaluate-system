package com.hoperun.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.domain.ExamStruct;
import com.hoperun.mapper.ExamStructMapper;

@Service
public class ExamStructService
{
private static Logger logger = LoggerFactory.getLogger(ExamStructService.class);
    
    @Autowired
    ExamStructMapper examStructMapper;
    
    public boolean insertExamStructPojo(ExamStruct exam)
    {
        try
        {
            examStructMapper.insertSelective(exam);
        }
        catch(Exception e)
        {
            logger.error("to insert exam struct pojo error");
            return false;
        }
        return true;
    }
    
    public Map<String, Object> getExamStructPojo(int colExamId)
    {
        Map<String, Object> out = null;
        
        try{
            List<ExamStruct> data = examStructMapper.getExamStructPojo(colExamId);
            if(data != null)
            {
                out = new HashMap<String, Object>();
                out.put("infos", data);
            }
        }
        catch(Exception e)
        {
            logger.error("to select exam struct pojo by colexamid error");
        }
        
        return out;
    }
}
