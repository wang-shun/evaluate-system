/*
 * 文 件 名:  ImportSubjectService.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hoperun.constant.SubjectAnwerMap;
import com.hoperun.constant.SubjectResultCode;
import com.hoperun.entity.Subject;
import com.hoperun.entity.SubjectAnswer;
import com.hoperun.entity.SubjectItem;
import com.hoperun.excelmodel.ExcelSubject;
import com.hoperun.service.AnswerService;
import com.hoperun.service.ImportSubjectService;
import com.hoperun.service.SubjectItemService;
import com.hoperun.service.SubjectService;
import com.hoperun.util.TimeUtil;
import static com.hoperun.constant.SubjectAnwerMap.SUBJECTAWERMAP;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Service
public class ImportSubjectServiceImpl implements ImportSubjectService {
	private Logger log = LoggerFactory.getLogger(ImportSubjectServiceImpl.class);

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private SubjectItemService subjectItemService;


	public String InsertSubjectAndAnswer(ExcelSubject excelSubject) {
		Subject subject=new Subject();
		subject.setCreateTime(TimeUtil.getCurrentTime());
		subject.setDefficultyWeight(Integer.valueOf(excelSubject.getDiffcutType()));
		subject.setIsOpen(Integer.valueOf(excelSubject.getIsUse()));
		subject.setSubjectContent(excelSubject.getContent());
		subject.setSubjectDbId(excelSubject.getSubjectDbId());
		subject.setSubjectType(Integer.valueOf(excelSubject.getSubjectType()));
		try {
			//新增題目
		subjectService.insert(subject);
          log.info("insert subject success subjectid is  "+subject.getId());
		} catch (Exception e) {
		log.error("insert subject error===========>"+e);
		return SubjectResultCode.RESULTCODE_FAIL;
		}
		 int subjectId=subject.getId();
		//新增答案
		 //答案列表
		 List<String>answerList=excelSubject.getAnswer();
		 //正确答案
		String[]rightAwer=excelSubject.getRightAnswer();
		 int size=answerList.size();
		 int defaultOrder=1;
		 for (int i = 0; i <size; i++) {
			 //答案详情表
			SubjectItem item=new SubjectItem();
			item.setSubjectId(subjectId);
			item.setOptionContent(answerList.get(i));
			item.setOptionOrder(defaultOrder<<i);
			subjectItemService.insert(item);
		}
		//正确答案表
		 SubjectAnswer subjectAnswer=new SubjectAnswer();
		 int defaultAnswer=0;
		 for (int j = 0; j < rightAwer.length; j++) {
			 defaultAnswer= SUBJECTAWERMAP.get(rightAwer[j])+defaultAnswer;
		}
		 subjectAnswer.setSubjectId(subjectId);
		 subjectAnswer.setSubjectAnswer(defaultAnswer);
		 try {
			answerService.insert(subjectAnswer);
		} catch (Exception e) {
			log.error("insert anwer error"+e);
			return SubjectResultCode.RESULTCODE_FAIL;
		}
		return SubjectResultCode.RESULTCODE_SUCESS;

	}
	
}
