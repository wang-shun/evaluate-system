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

import com.alibaba.fastjson.JSON;
import com.hoperun.domain.Exam;
import com.hoperun.domain.ExamAccount;
import com.hoperun.domain.ExamAll;
import com.hoperun.mapper.ExamAccountMapper;
import com.hoperun.mapper.ExamMapper;

@Service
public class ExamAccountService
{
    private static Logger logger = LoggerFactory.getLogger(ExamAccountService.class);
    
    @Autowired
    ExamAccountMapper examAccountMapper;
    
    @Autowired
    ExamMapper examMapper;
    
    public boolean insertOneExamAccount(ExamAccount bean)
    {
        try
        {
            examAccountMapper.insertSelective(bean);
        }
        catch(Exception e)
        {
            logger.error("insertOneExamAccount error ", e);
            return false;
        }
        return true;
    }
    
    public boolean updateOneExamAccount(ExamAccount bean)
    {
        try
        {
            examAccountMapper.updateByPrimaryKeySelective(bean);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
    
    public boolean updateExamIsPass(int paperId, int examId, int score)
    {
        Exam bean = examMapper.selectByPrimaryKey(examId);
        if(null != bean)
        {
            int passScore = bean.getColPassScore();
            int isPass = (passScore <= score) ? 1 : 0;
            
            //更新关联表中是否通过考试字段
            ExamAccount temp = new ExamAccount();
            temp.setId(paperId);
            temp.setColIsPass(isPass);
            return updateOneExamAccount(temp) && (passScore <= score);
        }
        else
        {
            return false;
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout=36000, rollbackFor=Exception.class)
    public void addExamAccountList(List<ExamAccount> data)
    {
        if(null != data)
        {
            for(ExamAccount one : data)
            {
                if(!insertOneExamAccount(one))
                {
                    logger.error("OneExamAccount insert into db error:"+JSON.toJSONString(one));
                }
            }
        }
    }
    
    public Map<String, Object> getExamAccountList(String accountId, int parerCount)
    {
        Map<String, Object> out = null;
        try
        {
            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("accountId", accountId);
            obj.put("parerCount", parerCount);
            List<ExamAll> data = examAccountMapper.selectByAccountId(obj);
            out = new HashMap<String, Object>();
            out.put("infos", data);
        }
        catch(Exception e)
        {
            logger.error("Error happend at getExamAccountList.");
        }
        return out;
    }
}
