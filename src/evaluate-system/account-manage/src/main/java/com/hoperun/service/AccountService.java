package com.hoperun.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.hoperun.commons.domain.Account;
import com.hoperun.domain.INFO;
import com.hoperun.mapper.AccountMapper;
import com.hoperun.utils.ExcelUtil;

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
    
    @Resource(name = "excelUtil")
    private ExcelUtil excelUtil;
    
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
    
    public String updatePassword(Account account)
    {
        logger.info("Enter service method updatePassword.");
        String out = null;
        int data = 0;
        try
        {
            data = accountMapper.updatePassword(account);
        }
        catch(Exception e)
        {
            logger.error("error at updatePassword. {}", e);
            out = "update password error!!!";
        }
        
        if(data == 0)
        {
            out = "update password error!!!";
        }
        
        
        return out;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout=36000, rollbackFor=Exception.class)
    public String accountToDepartment(List<String> ids, int departmentId)
    {
        logger.info("Enter service method accountToDepartment.");
        String out = null;
        int data = 0;
        Map<String, Object> para = new HashMap<String, Object>();
        StringBuilder builder = new StringBuilder("");
        if(null != ids)
        {
            for(String one : ids)
            {
                builder.append("\"").append(one).append("\",");
            }
        }
        String temp = builder.toString();
        para.put("ids", temp.substring(0, temp.length()-1));
        para.put("departmentId", departmentId);
        try
        {
            data = accountMapper.accountToDepartment(para);
        }
        catch(Exception e)
        {
            logger.error("error at accountToDepartment. {}", e);
            out = "accountToDepartment error!!!";
        }
        
        if(data == 0)
        {
            out = "accountToDepartment error!!!";
        }
        
        
        return out;
    }
    
    public String accountAdd(Account account)
    {
        logger.info("Enter service method accountAdd.");
        String out = null;
        int data = 0;
        try
        {
            data = accountMapper.insertSelective(account);
        }
        catch(Exception e)
        {
            logger.error("error at accountAdd. {}", e);
            out = "accountAdd error!!!";
        }
        
        if(data == 0)
        {
            out = "accountAdd error!!!";
        }
        
        
        return out;
    } 
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout=36000, rollbackFor=Exception.class)
    public String accountDel(List<String> id)
    {
        logger.info("Enter service method accountDel.");
        String out = null;
        int data = 0;
        try
        {
            if(null != id)
            {
                for(String one : id)
                {
                    data = accountMapper.deleteByPrimaryKey(one);
                }
            }
        }
        catch(Exception e)
        {
            logger.error("error at accountDel. {}", e);
            out = "accountDel error!!!";
        }
        
        if(data == 0)
        {
            out = "accountDel error!!!";
        }
        
        
        return out;
    }
    
    public String accountUpdate(Account account)
    {
        logger.info("Enter service method accountUpdate.");
        String out = null;
        int data = 0;
        try
        {
            data = accountMapper.updateByPrimaryKeySelective(account);
        }
        catch(Exception e)
        {
            logger.error("error at accountUpdate. {}", e);
            out = "accountUpdate error!!!";
        }
        
        if(data == 0)
        {
            out = "accountUpdate error!!!";
        }
        
        
        return out;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout=36000, rollbackFor=Exception.class)
    public String importAccount(MultipartFile file, Integer departmentId, String creatorId) throws RuntimeException, IOException
    {
        logger.info("Enter service method importAccount.");
        String out = null;
        
        Map<INFO, List<Account>> data = excelUtil.readAccountFromExcel(file.getInputStream(), file.getOriginalFilename(), creatorId, departmentId);
        
        List<Account> right = data.get(INFO.right);
        List<Account> error = data.get(INFO.error);
        
        if(error.size() > 0)
        {
            out = JSON.toJSONString(error);
        }
        else
        {
            try
            {
                for(Account record : right)
                {
                    accountMapper.insertSelective(record);
                }
            }
            catch(Exception e)
            {
                out = "Error happend at importAccount!";
                logger.error("Error happend at importAccount! {}", e);
            }
        }
        
        return out;
    }
    
    public String resetPassword(Account account)
    {
        logger.info("Enter service method resetPassword.");
        String out = null;
        int data = 0;
        try
        {
            data = accountMapper.resetPassword(account);
        }
        catch(Exception e)
        {
            logger.error("error at resetPassword. {}", e);
            out = "reset password error!!!";
        }
        
        if(data == 0)
        {
            out = "reset password error!!!";
        }
        
        return out;
    }
    
    public Account findOneAccount(Account account)
    {
        logger.info("Enter service method findOneAccount.");
        return accountMapper.selectOne(account);
    }
}
