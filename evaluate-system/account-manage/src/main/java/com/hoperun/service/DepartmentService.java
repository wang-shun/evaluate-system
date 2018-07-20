package com.hoperun.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.domain.DepartmentTreeBean;
import com.hoperun.dto.Account;
import com.hoperun.dto.Department;
import com.hoperun.mapper.AccountMapper;
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
    
    @Autowired
    AccountMapper accountMapper;
    
    /**
     * 通过父部门id获取子部门的信息列表
     * <功能详细描述>
     * @param colParentId
     * @return
     */
    public List<Department> findSonDepartment(int colParentId)
    {
        logger.info("Enter into service method findSonDepartment!");
        return departmentMapper.selectByParentId(colParentId);
    }
    
    /**
     * 获取一个部门下子部门的树结构
     * <功能详细描述>
     * @param departmentId
     * @return
     */
    public List<DepartmentTreeBean> getDepartmentTree(int departmentId)
    {
        logger.info("Enter into service method getDepartmentTree!");
        List<DepartmentTreeBean> out = new ArrayList<DepartmentTreeBean>();
        
        //数据库查询树结构需要借助存储过程，稍麻烦，数据量少现在先查到内存，代码递归进行处理
        List<Department> allData = departmentMapper.selectAll();
        if(null != allData)
        {
            out = getOneLevel(departmentId, allData);
        }
        
        return out;
    }
    
    /**
     * 递归获取树结构
     * <功能详细描述>
     * @param departmentId
     * @param allData
     * @return
     */
    private List<DepartmentTreeBean> getOneLevel(int departmentId, List<Department> allData)
    {
        List<DepartmentTreeBean> out = new ArrayList<DepartmentTreeBean>();
        DepartmentTreeBean temp = null;
        for(Department one : allData)
        {
            if(one.getColParentId().equals(departmentId))
            {
                temp = new DepartmentTreeBean();
                temp.setId(one.getId());
                temp.setColName(one.getColName());
                temp.setColDesc(one.getColDesc());
                temp.setSonList(getOneLevel(one.getId(), allData));
                out.add(temp);
            }
        }
        return out;
    }
    
    /**
     * 通过部门id获取该部门下所有账号信息
     * <功能详细描述>
     * @param departmentId
     * @return
     */
    public List<Account> findAccountByDepartmentId(int departmentId)
    {
        logger.info("Enter into service method findAccountByDepartmentId!");
        return accountMapper.selectAllByDepartmentId(departmentId);
    }
    
    /**
     * 查询末级部门
     * <功能详细描述>
     * @return
     */
    public List<Integer> selectLastParentId()
    {
        logger.info("Enter into service method selectLastParentId!");
        return departmentMapper.selectLastParentId();
    }
}
