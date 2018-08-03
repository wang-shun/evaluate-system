package com.hoperun.service;

import java.util.ArrayList;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.hoperun.commons.domain.Account;
import com.hoperun.commons.domain.ResponseMsg;
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
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public boolean examToRepeat(int examId, String creatorId)
    {
        // 先获取一场考试开放时间内考生考试次数满了还没通过考试的考生ID
        List<ExamAccount> dataList = null;
        try
        {
            dataList = examAccountMapper.examNotAccess(examId);
        }
        catch (Exception e)
        {
            logger.error("get examNotAccess error ", e);
            return false;
        }
        
        if (null == dataList || dataList.size() == 0)
        {
        	logger.info("have no account not access exam.");
        	return true;
        }
        
        // 查询一场考试待考的成员
        List<ExamAccount> users = examAccountMapper.examNeedDo(examId);
        
        List<ExamAccount> userList = new ArrayList<ExamAccount>();
        
        for(ExamAccount one : dataList)
        {
            if(null != users)
            {
                for(ExamAccount temp : users)
                {
                    if(one.getColAccountId().equals(temp.getColAccountId()))
                    {
                        continue;
                    }
                    userList.add(one);
                }
            }
        }
        
        // 通过试卷id获取试卷的基本信息
        Exam bean = examMapper.selectByPrimaryKey(examId);
        int limit = 0;
        if (null != bean)
        {
            limit = bean.getColLimitCount();
        }
        else
        {
            return false;
        }
        
        // 通过上面两个信息重新建立起试卷id和考生id的关联关系
        for (ExamAccount one : userList)
        {
            one.setId(null);
        	one.setColParerCount(0);
            one.setColLimitCount(limit);
            one.setColCreatorId(creatorId);
            one.setColCreateTime(System.currentTimeMillis());
            if(!insertOneExamAccount(one))
            {
            	return false;
            }
        }
        
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public List<Account> examAccountAddDep(Integer examid, Integer departmentId)
    {
        List<Account> out = new ArrayList<Account>();
        
        //根据部门ID去account-manage中获取该部门下账户信息
        // TODO
        logger.info("get account by departmenid {} from account-manage service.", departmentId);
        String url = "http://account-manage/department/{departmentId}";
        Map<String, Object> params = new HashMap<>();
        params.put("departmentId", departmentId);
        
        ResponseMsg result = null;
        try
        {
            result = restTemplate.getForObject(url, ResponseMsg.class, params);
        }
        catch (RestClientException e)
        {
            logger.error("get account by departmenid error {}", e);
        }
        
        List<Account> accountList = null;
        
        if(null != result && result.getData() != null)
        {
            String str = JSON.toJSONString(result.getData().get("accountInfos"));
            logger.error("xxxx {}", str);
            accountList = JSON.parseArray(str, Account.class);
        }
        
        //通过examid查询这场考试已关联的账户
        List<ExamAccount> linkUsers = examAccountMapper.selectByExamId(examid);
        if (null != accountList)
        {
            boolean is = true;
            for (int i=0; i<accountList.size(); i++)
            {
                is = true;
                if (null != linkUsers)
                {
                    for (ExamAccount temp : linkUsers)
                    {
                        if (accountList.get(i).getId().equals(temp.getColAccountId()))
                        {
                            is = false;
                            break;
                        }
                    }
                }
                if (is)
                {
                    out.add(accountList.get(i));
                }
            }
        }
        return out;
    }
    
    public boolean examAccountAdd(Integer examId, String creatorId, List<String> accountIds)
    {
        if(null != accountIds && null != examId)
        {
            List<ExamAccount> data = new ArrayList<ExamAccount>();
            
            // 通过试卷id获取试卷的基本信息
            Exam bean = examMapper.selectByPrimaryKey(examId);
            int limit = 0;
            if (null != bean)
            {
                limit = bean.getColLimitCount();
            }
            else
            {
                return false;
            }
            ExamAccount temp = null;
            for(String one : accountIds)
            {
                temp = new ExamAccount();
                temp.setColExamId(examId);
                temp.setColAccountId(one);
                temp.setColLimitCount(limit);
                temp.setColCreatorId(creatorId);
                temp.setColCreateTime(System.currentTimeMillis());
                data.add(temp);
            }
            
            // 关联关系入库
            addExamAccountList(data);
        }
        return true;
    }
    
    public boolean insertOneExamAccount(ExamAccount bean)
    {
        try
        {
            examAccountMapper.insertSelective(bean);
        }
        catch (Exception e)
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
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    
    public boolean updateExamIsPass(int paperId, int examId, int score)
    {
        Exam bean = examMapper.selectByPrimaryKey(examId);
        if (null != bean)
        {
            int passScore = bean.getColPassScore();
            int isPass = (passScore <= score) ? 1 : 0;
            
            //更新关联表中是否通过考试字段
            ExamAccount temp = new ExamAccount();
            temp.setId(paperId);
            temp.setColIsPass(isPass);
            
            //TODO 要先查已考次数，将其+1
            ExamAccount obj = examAccountMapper.selectByPrimaryKey(paperId);
            if(obj == null || obj.getColParerCount() == null)
            {
                return false;
            }
            temp.setColParerCount(obj.getColParerCount()+1);
            return updateOneExamAccount(temp) && (passScore <= score);
        }
        else
        {
            return false;
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void addExamAccountList(List<ExamAccount> data)
    {
        if (null != data)
        {
            for (ExamAccount one : data)
            {
                if (!insertOneExamAccount(one))
                {
                    logger.error("OneExamAccount insert into db error:" + JSON.toJSONString(one));
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
            obj.put("nowTime", System.currentTimeMillis());
            List<ExamAll> data = examAccountMapper.selectByAccountId(obj);
            out = new HashMap<String, Object>();
            out.put("infos", data);
        }
        catch (Exception e)
        {
            logger.error("Error happend at getExamAccountList.");
        }
        return out;
    }
}
