package com.hoperun.web;

import com.hoperun.domain.CODE;
import com.hoperun.domain.ResponseMsg;
import com.hoperun.service.DepartmentService;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 部门接口
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月12日]
 * @since  [云服务/模块版本]
 */
@RestController
public class DepartmentController {
    
    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    
    @Autowired
    DepartmentService departmentService;
    
    @RequestMapping(value="/department", method= RequestMethod.POST)
    @ResponseBody
    public ResponseMsg sonDepartmentGet(HttpServletRequest request) {
        logger.info("Enter controller method sonDepartmentGet!");
        ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
        Map<String, Object> data = new HashMap<String, Object>();
        int colParentId = Integer.parseInt(request.getParameter("parentId"));
        data.put("sonDepartmentInfos", departmentService.findSonDepartment(colParentId));
        resp.setData(data);
        return resp;
    }
}

