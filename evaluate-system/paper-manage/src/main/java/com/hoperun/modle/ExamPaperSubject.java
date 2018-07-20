/*
 * 文 件 名:  ExamPaperSubject.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月17日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.modle;

import java.io.Serializable;
import java.util.List;

/**考试试卷试题
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月17日]
 * @since  [云服务/模块版本]
 */
public class ExamPaperSubject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7132669201644555836L;

    private List<Subject> singleSubjects;
    
    private List<Subject> multipleSubjects;
    
    public List<Subject> getSingleSubjects()
    {
        return singleSubjects;
    }
    
    public void setSingleSubjects(List<Subject> singleSubjects)
    {
        this.singleSubjects = singleSubjects;
    }
    
    public List<Subject> getMultipleSubjects()
    {
        return multipleSubjects;
    }
    
    public void setMultipleSubjects(List<Subject> multipleSubjects)
    {
        this.multipleSubjects = multipleSubjects;
    }
    
}
