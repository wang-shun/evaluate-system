package com.hoperun.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hoperun.commons.domain.CODE;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.domain.ExamStruct;
import com.hoperun.service.ExamStructService;

/**
 * 试卷结构、模块
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月13日]
 * @since  [云服务/模块版本]
 */
@RestController
public class ExamStructController {

    private static Logger logger = LoggerFactory.getLogger(ExamStructController.class);
    
    @Autowired
    ExamStructService examStructService;
    
    /**
     * 插入单个试卷的结构
     * <功能详细描述>
     * @param examStruct
     * @return
     */
    @RequestMapping(value = "/examstruct", method = RequestMethod.POST)
    public ResponseMsg createExamStruct(@RequestBody ExamStruct examStruct) {
        logger.info("Enter into method createExamStruct.");
        ResponseMsg out = null;
        out = examStructService.insertExamStructPojo(examStruct) ?  new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR); 
        return out;
    }
    
    /**
     * 根据examid查询考试的结构
     * <功能详细描述>
     * @param examid
     * @return
     */
    @RequestMapping(value = "/examstruct/{examid}", method = RequestMethod.GET)
    public ResponseMsg getExamStruct(@PathVariable Integer examid) {
        logger.info("Enter into method getExamStruct.");
        ResponseMsg out = null;
        Map<String, Object> data = examStructService.getExamStructPojo(examid);
        if(null == data)
        {
            out = new ResponseMsg(CODE.UNEXP_ERR); 
        }
        else
        {
            out = new ResponseMsg(CODE.RESP_SUC);
            out.setData(data);
        }
        return out;
    }
}
