/*
 * 文 件 名:  PaperStatusMap.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月18日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.util;

import java.util.HashMap;
import java.util.Map;

import com.hoperun.constant.PapersatusConstant;



/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月18日]
 * @since  [云服务/模块版本]
 */
public class PaperStatusMap
{
    public static Map<Integer, String> PAPERSTATUSMAP=new HashMap<Integer, String>();
    static {
        PAPERSTATUSMAP.put(PapersatusConstant.TO_START, PapersatusConstant.TO_START_DESC);
        PAPERSTATUSMAP.put(PapersatusConstant.ANSWEING, PapersatusConstant.ANSWEING_DESC);
        PAPERSTATUSMAP.put(PapersatusConstant.PASS, PapersatusConstant.PASS_DESC);
        PAPERSTATUSMAP.put(PapersatusConstant.DONTPASS, PapersatusConstant.DONTPASS_DESC);
      
        
    }
}
