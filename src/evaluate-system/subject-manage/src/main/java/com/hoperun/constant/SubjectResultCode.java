/*
 * 文 件 名:  SubjectResultCode.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.constant;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
public interface SubjectResultCode {
	static final String RESULTCODE_SUCESS = "0";
	static final String RESULTCODE_HASSUBJECT = "0006";
	static final String RESULTCODE_SUCESS_DESC = "成功";
	static final String RESULTCODE_FAIL = "1";
	static final String RESULTCODE_FAIL_DESC = "失败";
	
	static final String QUESTIONTEMPLATE_DOWNLOAD_FAIL = "0001";
	static final String QUESTIONTEMPLATE_DOWNLOAD_FAIL_DESC = "试题模板下载失败";
	static final String QUESTIONPLATE_DOWNLOAD_SUCESS = "0000";
	static final String QUESTIONPLATE_DOWNLOAD_SUCESS_DESC = "试题模板下载成功";
	static final String QUESTIONPLATE_IS_NOTEXISTS = "0003";
	static final String QUESTIONPLATE_IS_NOTEXISTS_DESC = "试题模板不存在";

}
