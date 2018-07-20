package com.hoperun.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.dto.Account;
import com.hoperun.mapper.AccountMapper;

/**
 * 用户账户服务
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月12日]
 * @since  [云服务/模块版本]
 */
@Service
public class AccountService
{
    private static Logger logger = LoggerFactory.getLogger(AccountService.class);
        
    @Autowired
    AccountMapper accountMapper;
    
    public boolean isAccessValidat(Account account)
    {
        logger.info("Enter service method isAccessValidat.");
        Map<String, Object> data = accountMapper.selectBycondition(account);
        if(data == null)
        {
            return false;
        }
        return Integer.parseInt(String.valueOf(data.get("numSize"))) !=0 ;
    }
    
    public Account findOneAccount(Account account)
    {
        logger.info("Enter service method findOneAccount.");
        return accountMapper.selectOne(account);
    }
}
