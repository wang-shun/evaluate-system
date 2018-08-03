/*
 * 文 件 名:  SubjectServiceImpl.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoperun.commons.domain.ResultCode;
import com.hoperun.constant.SubjectResultCode;
import com.hoperun.dao.SubjectAnswerMapper;
import com.hoperun.dao.SubjectItemMapper;
import com.hoperun.dao.SubjectMapper;
import com.hoperun.entity.Subject;
import com.hoperun.entity.SubjectAnswer;
import com.hoperun.entity.SubjectItem;
import com.hoperun.service.SubjectService;
import com.hoperun.util.page.PageUtil;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Service
public class SubjectServiceImpl implements SubjectService {
	private Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private SubjectItemMapper subjectItemMapper;
	@Autowired
	private SubjectAnswerMapper subjectAnswerMapper;

	@Override
	public String deleteByPrimaryKey(Integer id) {
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
		try {
			 subjectMapper.deleteByPrimaryKey(id);
			 //删除题目详情
			 subjectItemMapper.deleteBySubjectId(id);
			 //删除答案
			 subjectAnswerMapper.deleteBySubjectId(id);
			resultCode = SubjectResultCode.RESULTCODE_SUCESS;
		} catch (Exception e) {
			log.error("delete subject by id error " + e);
		}
		return resultCode;
	}

	@Override
	public int insert(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.insert(record);
	}

	@Override
	public Subject selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return subjectMapper.selectByPrimaryKey(id);
	}

	@Override
	public String updateByPrimaryKey(Subject record) {
		// TODO Auto-generated method stub
	    String resultCode = SubjectResultCode.RESULTCODE_FAIL;
        try {
            subjectMapper.updateByPrimaryKey(record);
             
            resultCode = SubjectResultCode.RESULTCODE_SUCESS;
        } catch (Exception e) {
            log.error("delete subject by id error " + e);
        }
        return resultCode;
	}

	@Override
	public List<Subject> queyAllSubject()  {
		// TODO Auto-generated method stub
		return subjectMapper.queyAllSubject();
	}

	

	@Override
	public List<Subject> queySubjectByType(Integer subjectType) {
		// TODO Auto-generated method stub
		return subjectMapper.queySubjectByType(subjectType);
	}

	@Override
	public PageInfo<Subject> queySubjectByContent(String subjectContent,Integer subjectDbId,Integer subjectType,Integer isOpen,Integer defficultyWeight,Integer pageNum,Integer pageSize) {
		// TODO Auto-generated method stub
		pageNum = pageNum == null ? PageUtil.DEFULT_PAGENUM : pageNum;
		pageSize = pageSize == null ? PageUtil.DEFULT_PAGESIZE : pageSize;
		PageHelper.startPage(pageNum, pageSize);
	    Subject subject=new Subject();
	    subject.setSubjectContent(subjectContent);
	    subject.setSubjectDbId(subjectDbId);
	    subject.setSubjectType(subjectType);
	    subject.setIsOpen(isOpen);
	    subject.setDefficultyWeight(defficultyWeight);
		List<Subject>list= subjectMapper.queySubjectByContent(subject);
		PageInfo<Subject>info=new PageInfo<>(list);
		return info;
	}

	@Override
	public List<Subject> queySubjectBysubjectDbId(Integer subjectDbId) {

			return subjectMapper.queySubjectBysubjectDbId(subjectDbId);
		}

    @Override
    public String addSubject(Subject insertSubjectModle) {
        String resultCode=ResultCode.RESULTCODE_SUCESS;
        try {
          //新增题目
            insertSubjectModle.setCreateTime(System.currentTimeMillis());
            subjectMapper.insert(insertSubjectModle);
            //题目id
            int subjectId=insertSubjectModle.getId();
            //新增题目详情
            List<SubjectItem>items=insertSubjectModle.getItems();
            for (SubjectItem subjectItem : items) {
                subjectItem.setSubjectId(subjectId);
                subjectItemMapper.insert(subjectItem);
                
            }
            SubjectAnswer answer=insertSubjectModle.getAnswer();
            //新增答案
            answer.setSubjectId(subjectId);
            subjectAnswerMapper.insert(answer);
            
        } catch (Exception e) {
           log.error("insert subjectrrror {}"+e);
           resultCode=ResultCode.RESULTCODE_FAIL;
        }
        
         return resultCode;
    }
	

}
