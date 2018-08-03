/*
 * 文 件 名: ExamPaperService.java 版 权: Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved. 描 述: <描述> 修 改 人:
 * mWX576942 修改时间: 2018年7月17日 修改版本: <跟踪单号>
 */
package com.hoperun.service;

import java.util.List;
import java.util.Map;

import com.hoperun.entity.ExamPaper;
import com.hoperun.modle.ExamStruct;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月17日]
 * @since [云服务/模块版本]
 */
public interface ExamPaperService {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param record
     * @return
     */
    int insert(ExamPaper record);

    /**
     * 查询
     * @param id
     * @return
     */
    ExamPaper selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKey(ExamPaper record);

    /**
     * 查询
     * @param accountId
     * @return
     */
    Map<String, Object> getExamAccountList(String accountId);

}
