/*
 * 文 件 名:  ImportSubjectService.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service;
import com.hoperun.entity.Subject;
import com.hoperun.excelmodel.ExcelSubject;


/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */

public interface ImportSubjectService {

	

	public String insertSubjectAndAnswer(ExcelSubject excelSubject);

}
