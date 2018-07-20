package com.hoperun.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoperun.commons.domain.CODE;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.domain.ExamAccount;
import com.hoperun.service.ExamAccountService;

/**
 * 试卷账号关联
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月16日]
 * @since  [云服务/模块版本]
 */
@RestController
public class ExamAccountController {
    
    private static Logger logger = LoggerFactory.getLogger(ExamAccountController.class);
    
    @Autowired
    ExamAccountService examAccountService;
    
    /**
     * 一个账号和考试的关联
     * <功能详细描述>
     * @param examAccount
     * @return
     */
    @RequestMapping(value = "/examaccount", method = RequestMethod.POST)
    public ResponseMsg addOneExamAccount(@RequestBody ExamAccount examAccount) {
        logger.info("Enter into controller method addOneExamAccount.");
        ResponseMsg out = null;
        out = examAccountService.insertOneExamAccount(examAccount) ?  new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR); 
        return out;
    }
    
    /**
     * 更新一个账号和考试的关联关系信息
     * <功能详细描述>
     * @param examAccount
     * @return
     */
    @RequestMapping(value = "/examaccount", method = RequestMethod.PUT)
    public ResponseMsg updateOneExamAccount(@RequestBody ExamAccount examAccount) {
        logger.info("Enter into controller method updateOneExamAccount.");
        ResponseMsg out = null;
        if(null == examAccount.getId())
        {
            out = new ResponseMsg(CODE.UNEXP_ERR); 
            out.setMessage("you need to translate index id.");
            return out;
        }
        out = examAccountService.updateOneExamAccount(examAccount) ?  new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR); 
        return out;
    }
    
    /**
     * 更新是否通过考试字段并返回这个信息给paper-manage
     * <功能详细描述>
     * @param body
     * @return
     */
    @RequestMapping(value = "/examispass", method = RequestMethod.GET)
    public ResponseMsg updateExamIsPass(@RequestParam Integer examPaperId, @RequestParam Integer examId, @RequestParam Integer score) {
        logger.info("Enter into controller method updateExamIsPass.");
        ResponseMsg out = null;
        
        out = examAccountService.updateExamIsPass(examPaperId, examId, score) ?  new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR); 
        return out;
    }
    
    /**
     * 一场考试对应多个账户的录入
     * <功能详细描述>
     * @param body
     * @return
     */
    @RequestMapping(value = "/examaccountlist", method = RequestMethod.POST)
    public ResponseMsg addExamAccountList(@RequestBody String body) {
        logger.info("Enter into controller method addExamAccountList."+body);
        ResponseMsg out = null;
        JSONObject json = JSON.parseObject(body);
        Integer examId =  json.getIntValue("examId");
        List<String> ids = JSON.parseArray(String.valueOf(json.get("ids")), String.class);
        int limitCount = json.getIntValue("limitCount");
        String creatId = json.getString("creatId");
        List<ExamAccount> examAccountList = new ArrayList<ExamAccount>();
        if(null != ids)
        {
            ExamAccount bean = null;
            for(String one : ids)
            {
                bean = new ExamAccount();
                bean.setColAccountId(one);
                bean.setColExamId(examId);
                bean.setColCreatorId(creatId);
                bean.setColCreateTime(System.currentTimeMillis());
                bean.setColLimitCount(limitCount);
                examAccountList.add(bean);
            }
        }
        
        examAccountService.addExamAccountList(examAccountList);  
        out = new ResponseMsg(CODE.RESP_SUC); 
        return out;
    }
    
    /**
     * 根据用户id查询其下所有的待考试
     * <功能详细描述>
     * @param accountid
     * @return
     */
    @RequestMapping(value = "/examaccount/{accountid}/{parercount}", method = RequestMethod.GET)
    public ResponseMsg getExamAccountList(@PathVariable String accountid, @PathVariable Integer parercount) {
        logger.info("Enter into controller method getExamAccountList.");
        ResponseMsg out = null;
        Map<String, Object> data = examAccountService.getExamAccountList(accountid, parercount);
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
