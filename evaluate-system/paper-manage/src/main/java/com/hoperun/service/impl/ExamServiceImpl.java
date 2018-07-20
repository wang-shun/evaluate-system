/*
 * 文 件 名:  ExamServiceImpl.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月17日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hoperun.dao.ExamPaperMapper;
import com.hoperun.entity.ExamPaper;
import com.hoperun.modle.ExamStruct;
import com.hoperun.modle.Subject;
import com.hoperun.service.ExamPaperService;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月17日]
 * @since  [云服务/模块版本]
 */
@Service
public class ExamServiceImpl implements ExamPaperService
{
    private static Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);
    
    @Autowired
    private ExamPaperMapper examPaperMapper;

    
    @Override
    public int deleteByPrimaryKey(Integer id)
    {

        return examPaperMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public int insert(ExamPaper record)
    {
      
        return examPaperMapper.insert(record);
    }
    
    @Override
    public ExamPaper selectByPrimaryKey(Integer id)
    {
        // TODO Auto-generated method stub
        return examPaperMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public int updateByPrimaryKey(ExamPaper record)
    {
        // TODO Auto-generated method stub
        return examPaperMapper.updateByPrimaryKey(record);
    }
    
    
    public Map<String, Object> getExamAccountList(String accountId)
    {
        Map<String, Object> out = null;
        try
        {
            //查所有
            if("ALL".equals(accountId))
            {
                accountId = null;
            }
            List<ExamPaper> data = examPaperMapper.getExamAccountList(accountId);
            if (data != null)
            {
                out = new HashMap<String, Object>();
                out.put("infos", data);
            }
        }
        catch (Exception e)
        {
            logger.error("service getExamAccountList error!");
        }
        
        return out;
    }
    
    public Map<String, Object> getExamListByExamId(int examId)
    {
        Map<String, Object> out = null;
        try
        {
            String examIdStr = String.valueOf(examId);
            List<ExamPaper> data = examPaperMapper.getExamListByExamId(examIdStr);
            if (data != null)
            {
                out = new HashMap<String, Object>();
                out.put("infos", data);
            }
        }
        catch (Exception e)
        {
            logger.error("service getExamAccountList error! {}", e);
        }
        
        return out;
    }
    
   
    
}
