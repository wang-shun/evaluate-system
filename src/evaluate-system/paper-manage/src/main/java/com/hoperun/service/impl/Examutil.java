package com.hoperun.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.constant.PapersatusConstant;
import com.hoperun.entity.ExamPaper;
import com.hoperun.modle.ExamPaperEntity;
import com.hoperun.papermodle.ExamSubject;
import com.hoperun.util.log.InterfaceLog;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
@Component("examutil")
public class Examutil {
	private Logger log = LoggerFactory.getLogger(Examutil.class);
	@Autowired
	private RestTemplate restTemplate;

	public ExamSubject[] createPaper(String subjectType, Integer subjectDbId, Integer subjectCount,
			Integer defficultyWeight) {
		String url = "http://subject-manage/examPaperSubjects?subjectType={subjectType}&subjectDbId={subjectDbId}&subjectCount={subjectCount}&defficultyWeight={defficultyWeight}";
		  InterfaceLog.interfaceInfoLog("createPaper  subjectType " + subjectType + "subjectDbId " + subjectDbId + "subjectCount "
	            + subjectCount+"from paper-manage to subjectmanage url "+url);
		Map<String, Object> params = new HashMap<>();
		params.put("subjectType", subjectType);
		params.put("subjectDbId", subjectDbId);
		params.put("subjectCount", subjectCount);
		params.put("defficultyWeight", defficultyWeight);

		ExamSubject[] result = null;
		try {
			result = restTemplate.getForObject(url, ExamSubject[].class, params);
		} catch (RestClientException e) {
			InterfaceLog.interfaceErrorLog("get examsubject error" + e);
		}

		return result;
	}

	public ResponseMsg queryExamPass(Integer examPaperId, Integer examId, int score) {
		String url = "http://exam-manage/examispass?examPaperId={examPaperId}&examId={examId}&score={score}";
		InterfaceLog.interfaceInfoLog("from paper to exam  examPaperId=========="+examPaperId+"examId"+examId+"score" +score);
		Map<String, Object> params = new HashMap<>();
		params.put("examPaperId", examPaperId);
		params.put("examId", examId);
		params.put("score", score);
		ResponseMsg msg = restTemplate.getForObject(url, ResponseMsg.class, params);
		return msg;
	}

	public boolean validateEntity(ExamPaperEntity entity) {
		boolean flag = true;
		if ("".equals(entity.getExamId()) || null == entity.getExamId()) {
			log.error("examid is null");
			return false;
		}
		if ("".equals(entity.getPaperName()) || null == entity.getPaperName()) {
			log.error("paperName is null");
			return false;
		}
		if ("".equals(entity.getPaperOwnerId()) || null == entity.getPaperOwnerId()) {
			log.error("PaperOwnerId is null");
			return false;
		}
		if ("".equals(entity.getSubjectDbId()) || null == entity.getSubjectDbId()) {
			log.error("SubjectDbId is null");
			return false;
		}
		if (0 == entity.getExamStructs().size() || null == entity.getExamStructs()) {
			log.error("ExamStructs is null");
			return false;
		}
		return flag;

	}

	public ExamPaper generateExamPaper(ExamPaperEntity entity) {
		ExamPaper record = new ExamPaper();
		record.setEndTime(entity.getEndTime());
		record.setPaperId(entity.getPaperId());
		record.setExamId(entity.getExamId());
		record.setIpaddr(entity.getIpaddr());
		record.setPaperName(entity.getPaperName());
		record.setPaperOwnerId(entity.getPaperOwnerId());
		log.info("getPaperOwnerId==================================================================="+entity.getPaperOwnerId());
		record.setPaperStatus(PapersatusConstant.TO_START);
		record.setPaperStatusDesc(PapersatusConstant.TO_START_DESC);
		record.setStartTime(entity.getStartTime());
		return record;

	}

	public List<ExamSubject> initExamSubject(ExamSubject[] examSubjects, List<ExamSubject> list) {
		if (null==list||list.size() == 0) {
			list = new ArrayList<>();
		}
		for (int i = 0; i < examSubjects.length; i++) {
			list.add(examSubjects[i]);
		}
		return list;

	}
}
