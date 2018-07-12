package com.hoperun.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hoperun.domain.CODE;
import com.hoperun.domain.ResponseMsg;
import com.hoperun.dto.Account;
import com.hoperun.service.AccountService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 账户接口
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月12日]
 * @since  [云服务/模块版本]
 */
@Controller
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;
    
    /**
     * 登录验证
     * <功能详细描述>
     * @param request
     * @return
     */
    @RequestMapping(value="/accessvalidat", method= RequestMethod.POST)
    @ResponseBody
    public ResponseMsg accessValidat(HttpServletRequest request) {
        logger.info("Enter controller method accessValidat!");
        ResponseMsg resp = null;
        String roleType = request.getParameter("roleType");
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        Account account = new Account();
        account.setColRoleId(Integer.parseInt(roleType));
        account.setColAccountName(name);
        account.setId(id);
        account.setColPassowrd(pwd);
        boolean is = accountService.isAccessValidat(account);
        resp = is ? new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR); 
        return resp;
    }
    
    /**
     * 查询单个账户
     * <功能详细描述>
     * @param request
     * @return
     */
    @RequestMapping(value="/account", method= RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getOneAccount(HttpServletRequest request, 
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String accountName) {
        logger.info("Enter controller method getOneAccount!");
        ResponseMsg resp = null;
        Account account = new Account();
        account.setColAccountName(accountName);
        account.setId(id);
        resp = new ResponseMsg(CODE.RESP_SUC);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("oneAccount", accountService.findOneAccount(account));
        resp.setData(data);
        return resp;
    }
}
