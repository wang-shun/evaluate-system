/*
 * 文 件 名:  PaperController.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月17日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.controller;

import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hoperun.commons.domain.ExamResponse;
import com.hoperun.commons.domain.Response;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.commons.domain.ResultCode;
import com.hoperun.constant.PaperResultCode;
import com.hoperun.constant.PapersatusConstant;
import com.hoperun.dao.ExamPaperSubjectMapper;
import com.hoperun.entity.ExamPaper;
import com.hoperun.modle.ExamPaperEntity;
import com.hoperun.modle.ExamStruct;
import com.hoperun.modle.SubjectSubmitModle;
import com.hoperun.papermodle.ExamPaperSubject;
import com.hoperun.papermodle.ExamSubject;
import com.hoperun.papermodle.SubjectAnswer;
import com.hoperun.service.ExamPaperService;
import com.hoperun.service.impl.Examutil;
import com.hoperun.util.ResopnseUtil;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月17日]
 * @since [云服务/模块版本]
 */
@RestController
public class PaperController {
	private Logger logger = LoggerFactory.getLogger(PaperController.class);

	@Autowired
	private ExamPaperService examPaperService;

	@Autowired
	private ExamPaperSubjectMapper examPaperSubjectMapper;

	@Autowired
	private Examutil examutil;

	/**
	 * 生成试卷 <功能详细描述>
	 * 
	 * @param httpServletRequest
	 * @param response
	 * @return
	 */
	@PostMapping("/examPapers")
	public ExamPaperSubject createPaper(HttpServletRequest httpServletRequest, HttpServletResponse response) {

		logger.info("begin to examPapers");
		ExamPaperSubject examPaperSubject = new ExamPaperSubject();
		try {
			ServletInputStream inputStream = httpServletRequest.getInputStream();
			String msg = IOUtils.toString(inputStream, "UTF-8");
			JSONObject object = JSONObject.parseObject(msg);
			ExamPaperEntity entity = JSONObject.toJavaObject(object, ExamPaperEntity.class);
			if (this.validateEntity(entity)) {
				examPaperSubject = this.insertPaper(entity, Integer.valueOf(entity.getSubjectDbId()));
			} else {
				logger.error("validate error");
				return null;
			}

		} catch (Exception e) {
			logger.error("createPaper error" + e);
		}

		return examPaperSubject;

	}

	/**
	 * 提交试卷 <功能详细描述>
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@PutMapping(value = "submitExamPaper")
	public ExamResponse submitExamPaper(HttpServletRequest httpServletRequest) {
		logger.info("begin to submitExamPaper");
		String resultCode = ResultCode.RESULTCODE_FAIL;
		int score=0;
		int isPass=0;
		try {
			ServletInputStream inputStream = httpServletRequest.getInputStream();
			String msg = IOUtils.toString(inputStream, "UTF-8");
			logger.info("================msg"+msg);
			JSONObject object = JSONObject.parseObject(msg);
			// 提交内容
			SubjectSubmitModle entity = JSONObject.toJavaObject(object, SubjectSubmitModle.class);
			// 更新考试答题表
			// 试卷id
			int examPaperId = entity.getExamPaperId();
			Long beginTime = entity.getBeginTime();
			Long endTime = entity.getEndTime();
			List<SubjectAnswer> answers = entity.getAnswers();
			for (SubjectAnswer subjectAnswer : answers) {
				int subjectId = subjectAnswer.getSubjectId();
				// 根据id查询答案
				 com.hoperun.entity.ExamPaperSubject examPaperSubject = examPaperSubjectMapper
						.selectBySubjectIdAndPaperId(subjectId,examPaperId);
				 if("".equals(examPaperSubject)||null==examPaperSubject) {
					 logger.error("examPaperSubjects is null");
					 return ResopnseUtil.getExamResponse(resultCode,score,isPass);
				 }
				examPaperSubject.setSubmitAnswer(subjectAnswer.getSubmitAnswer());
				// 判断是否正确
				int trueAnswer = examPaperSubject.getSubjectAnswer();
				// 提交的答案
				int submitAnswer = subjectAnswer.getSubmitAnswer();
				// 正确
				if (trueAnswer == submitAnswer) {
					examPaperSubject.setIsCorrect(1);
				} else {
					examPaperSubject.setIsCorrect(0);
				}
				// 更新提交答案表
				examPaperSubjectMapper.updateByPrimaryKey(examPaperSubject);
			}
			// 计算总分数
			 score = examPaperSubjectMapper.getSumScore(examPaperId);

			//
			// 更新表TB_EXAM_PAPER
			ExamPaper examPaper = examPaperService.selectByPrimaryKey(examPaperId);
			examPaper.setSubmitTime(endTime);
			examPaper.setScore(score);
			examPaper.setStartTime(beginTime);

			// 调用rest服务查看考试是否通过
			ResponseMsg rspmsg = examutil.queryExamPass(examPaper.getId(), Integer.valueOf(examPaper.getExamId()),
					score);
			logger.info("queryExamPass resultcode " + rspmsg.getCode());
			// 通过
			if (ResultCode.RESULTCODE_SUCESS_ISPASS.equals(rspmsg.getCode())) {
				examPaper.setPaperStatus(PapersatusConstant.PASS);
				examPaper.setPaperStatusDesc("通过");
				isPass=PapersatusConstant.PASS;
			} else {
				examPaper.setPaperStatus(PapersatusConstant.DONTPASS);
				examPaper.setPaperStatusDesc("未通过");
			}
			examPaperService.updateByPrimaryKey(examPaper);
			resultCode = ResultCode.RESULTCODE_SUCESS;
		}

		catch (Exception e) {
			logger.error("createPaper error" + e);
		}
		logger.info("end to submitExamPaper resultCode is " + resultCode);
		return ResopnseUtil.getExamResponse(resultCode,score,isPass);

	}

	/**
	 * 生成试卷 <功能详细描述>
	 * 
	 * @param entity
	 * @return
	 */
	public ExamPaperSubject insertPaper(ExamPaperEntity entity, Integer subjectDbId) {
		logger.info("begin to insertPaper ");
		ExamPaperSubject examPaperSubject = null;

		List<ExamStruct> list = entity.getExamStructs();

		ExamPaper examPaper = this.generateExamPaper(entity);
		// 新增
		try {
			examPaperService.insert(examPaper);
		} catch (Exception e) {
			logger.error("insert examPaper error" + e);
		}

		examPaperSubject = new ExamPaperSubject();
		logger.info("ExamStructlist size=====" + list.size());
		// 考试规则
		for (ExamStruct examStruct : list) {
			// 试题数
			int subjectCount = examStruct.getColSubjectCount();
			// 试题分数
			int subjectPoint = examStruct.getColSubjectPoint();
			// 试题类型
			int subjectType = examStruct.getColSubjectType();
			ExamSubject[] examSubject = examutil.createPaper(String.valueOf(subjectType), subjectDbId, subjectCount);
			logger.info("examSubject size=====" + examSubject.length);
			// 新增试题
			for (int i = 0; i < examSubject.length; i++) {
				ExamSubject examPapersubject = examSubject[i];
				com.hoperun.entity.ExamPaperSubject examPaperSubjectinsert = new com.hoperun.entity.ExamPaperSubject();
				examPaperSubjectinsert.setPoint(subjectPoint);
				examPaperSubjectinsert.setSubjectAnswer(examPapersubject.getAnswer().getSubjectAnswer());
				examPaperSubjectinsert.setExamPaper(examPaper.getId());
				examPaperSubjectinsert.setSubjectId(examPapersubject.getId());
				examPaperSubjectinsert.setSubjectType(examStruct.getColSubjectType());
				// 清空正确答案
				examPapersubject.setAnswer(null);
				try {
					examPaperSubjectMapper.insert(examPaperSubjectinsert);
				} catch (Exception e) {
					logger.error("examPaperSubjectinsert error" + e);
				}

			}
			switch (Integer.valueOf(subjectType)) {
			case 0:
				examPaperSubject.setSingleSubjects(examSubject);
				break;
			case 1:
				examPaperSubject.setMultiSubjects(examSubject);
				break;

			default:
				break;
			}

		}
		examPaperSubject.setPaperId(examPaper.getId());
		return examPaperSubject;

	}

	public ExamPaper generateExamPaper(ExamPaperEntity entity) {
		ExamPaper record = new ExamPaper();
		record.setEndTime(entity.getEndTime());
		record.setExamId(entity.getExamId());
		record.setIpaddr(entity.getIpaddr());
		record.setPaperName(entity.getPaperName());
		record.setPaperOwnerId(entity.getPaperOwnerId().toString());
		record.setPaperStatus(PapersatusConstant.TO_START);
		record.setPaperStatusDesc(PapersatusConstant.TO_START_DESC);
		record.setStartTime(entity.getStartTime());
		return record;

	}

	public boolean validateEntity(ExamPaperEntity entity) {
		boolean flag = true;
		if ("".equals(entity.getExamId()) || null == entity.getExamId()) {
			logger.error("examid is null");
			return false;
		}
		if ("".equals(entity.getPaperName()) || null == entity.getPaperName()) {
			logger.error("paperName is null");
			return false;
		}
		if ("".equals(entity.getPaperOwnerId()) || null == entity.getPaperOwnerId()) {
			logger.error("PaperOwnerId is null");
			return false;
		}
		if ("".equals(entity.getSubjectDbId()) || null == entity.getSubjectDbId()) {
			logger.error("SubjectDbId is null");
			return false;
		}
		if (0 == entity.getExamStructs().size() || null == entity.getExamStructs()) {
			logger.error("ExamStructs is null");
			return false;
		}
		return flag;

	}
}
