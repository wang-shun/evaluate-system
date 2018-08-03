package com.hoperun.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hoperun.commons.domain.CODE;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.service.impl.ExamServiceImpl;
/**
 * 试卷
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月17日]
 * @since  [云服务/模块版本]
 */
@RestController
public class ExamPaperController
{

    private static Logger logger = LoggerFactory.getLogger(ExamPaperController.class);
    
    @Autowired
    ExamServiceImpl examServiceImpl;
    
    /**
     * 查询一个或所有账户参加的所有考试
     * <功能详细描述>
     * @param accountId
     * @return
     */
    @RequestMapping(value ="/exampaper/{accountId}", method = RequestMethod.GET)
    public ResponseMsg getExamListByAccount(@PathVariable String accountId)
    {
        logger.info("Enter into controller method getExamListByAccount.");
        ResponseMsg out = null;
        Map<String, Object> data = examServiceImpl.getExamAccountList(accountId);
        if(null != data)
        {
        	
            out = new ResponseMsg(CODE.RESP_SUC);
            out.setData(data); 
        }
        else
        {
            out = new ResponseMsg(CODE.UNEXP_ERR);
        }
        return out;
    }
    
    /**
     * 
     * 
     * 查询一场考试的考试情况
     * <功能详细描述>
     * @param accountId
     * @return
     */
    @RequestMapping(value ="/exampaper-exam/{examId}", method = RequestMethod.GET)
    public ResponseMsg getExamListByExamId(@PathVariable Integer examId) 
    {
        logger.info("Enter into controller method getExamListByExamId.");
        ResponseMsg out = null;
        Map<String, Object> data = examServiceImpl.getExamListByExamId(examId);
        if(null != data)
        {
            out = new ResponseMsg(CODE.RESP_SUC);
            out.setData(data); 
        }
        else
        {
            out = new ResponseMsg(CODE.UNEXP_ERR);
        }
        return out;
    }
}
