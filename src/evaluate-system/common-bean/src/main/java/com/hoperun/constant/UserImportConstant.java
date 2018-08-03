/*
 * 文 件 名:  UserImportConstant.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年8月2日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.constant;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年8月2日]
 * @since  [云服务/模块版本]
 */
public interface UserImportConstant {
	
	static final String USER_TEMPLATE_PATH = "classpath:template/用户导入模板.xlsx";
	/**
	 * 用户下载模板不存在
	 */
	
	static final String USERTEMPLATE_DOWNLOAD_FAIL = "0001";
	static final String USERTEMPLATE_DOWNLOAD_FAIL_DESC = "用户模板下载失败";
	static final String USERTEMPLATE_DOWNLOAD_SUCESS = "0000";
	static final String USERTEMPLATE_DOWNLOAD_SUCESS_DESC = "用户模板下载成功";
	static final String USERTEMPLATE_IS_NOTEXISTS = "0003";
	static final String USERTEMPLATE_IS_NOTEXISTS_DESC = "用户模板不存在";

}
