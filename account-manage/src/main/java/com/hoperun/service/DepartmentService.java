package com.hoperun.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.dto.Department;
import com.hoperun.mapper.DepartmentMapper;

/**
 * 部门信息服务
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月12日]
 * @since  [云服务/模块版本]
 */
@Service
public class DepartmentService
{
    private static Logger logger = LoggerFactory.getLogger(DepartmentService.class);
    
    @Autowired
    DepartmentMapper departmentMapper;
    
    public List<Department> findSonDepartment(int colParentId)
    {
        logger.info("Enter into service method findSonDepartment!");
        return departmentMapper.selectByParentId(colParentId);
    }
}
