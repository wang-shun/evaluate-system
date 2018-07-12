package com.hoperun.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.mapper.RoleMapper;

/**
 * 角色信息服务
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月12日]
 * @since  [云服务/模块版本]
 */
@Service
public class RoleService
{
    private static Logger logger = LoggerFactory.getLogger(RoleService.class);
    
    @Autowired
    RoleMapper roleMapper;
}
