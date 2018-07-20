/*
 * 文 件 名:  SubjectResultCode.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.commons.domain;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
public interface ResultCode {
	static final String RESULTCODE_SUCESS = "0";
	static final String RESULTCODE_SUCESS_ISPASS = "0000";
	static final String RESULTCODE_SUCESS_DESC = "成功";
	static final String RESULTCODE_FAIL = "1";
	static final String RESULTCODE_FAIL_DESC = "失败";

}
