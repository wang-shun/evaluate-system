package com.hoperun.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.hoperun.domain.Exam;
import com.hoperun.domain.ExamAccount;
import com.hoperun.domain.ExamStruct;
import com.hoperun.service.ExamService;

/**
 * 试卷对象
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月13日]
 * @since  [云服务/模块版本]
 */
@RestController
public class ExamController {

    private static Logger logger = LoggerFactory.getLogger(ExamController.class);
        
    @Autowired
    ExamService examService;
    
    /**
     * 创建一份考试
     * <功能详细描述>
     * @param exam
     * @return
     */
    @RequestMapping(value = "/exam", method = RequestMethod.POST)
    public ResponseMsg createExam(@RequestBody Exam exam) {
        logger.info("Enter into method createExam.");
        ResponseMsg out = null;
        out = examService.insertExamPojo(exam) ?  new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR); 
        return out;
    }
    
    /**
     * 创建试题-规则-关联账号
     * <功能详细描述>
     * @param reqBody
     * @return
     */
    @RequestMapping(value = "/examination", method = RequestMethod.POST)
    public ResponseMsg createExamination(@RequestBody String reqBody)
    {
        logger.info("Enter into method createExam.");
        ResponseMsg out = null;
        
        //解析前台传过来的综合信息
        JSONObject json = JSON.parseObject(reqBody);
        Exam examBean = json.getJSONObject("exam").toJavaObject(Exam.class);
        examBean.setColCreateTime(System.currentTimeMillis());
        List<ExamStruct> examStruct = json.getJSONArray("examStruct").toJavaList(ExamStruct.class);
        
        JSONObject examAccountBean =  json.getJSONObject("examAccountList");
        List<String> ids = JSON.parseArray(String.valueOf(examAccountBean.get("ids")), String.class);
        int limitCount = examAccountBean.getIntValue("limitCount");
        String creatId = examAccountBean.getString("creatId");
        List<ExamAccount> examAccountList = new ArrayList<ExamAccount>();
        if(null != ids)
        {
            ExamAccount bean = null;
            for(String one : ids)
            {
                bean = new ExamAccount();
                bean.setColAccountId(one);
                bean.setColCreatorId(creatId);
                bean.setColCreateTime(System.currentTimeMillis());
                bean.setColLimitCount(limitCount);
                examAccountList.add(bean);
            }
        }
        
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("examBean", examBean);
        obj.put("examStruct", examStruct);
        obj.put("examAccountList", examAccountList);
        out = examService.createExamination(obj) ?  new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR); 
        return out;
    }
    
    /**
     * 查询一份考试
     * <功能详细描述>
     * @param examid
     * @return
     */
    @RequestMapping(value = "/exam/{examid}", method = RequestMethod.GET)
    public ResponseMsg getExam(@PathVariable Integer examid) {
        logger.info("Enter into method getExam.");
        ResponseMsg out = new ResponseMsg(CODE.RESP_SUC);
        out.setData(examService.getExamPojo(examid)); 
        return out;
    }
    
    /**
     * 查询所有已经发布的考试（管理员）
     * <功能详细描述>
     * @return
     */
    @RequestMapping(value = "/examall", method = RequestMethod.GET)
    public ResponseMsg getAllExam() {
        logger.info("Enter into method getAllExam.");
        ResponseMsg out = new ResponseMsg(CODE.RESP_SUC);
        out.setData(examService.getAllExamPojo()); 
        return out;
    }
}
