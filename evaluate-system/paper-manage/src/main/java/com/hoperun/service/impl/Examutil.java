package com.hoperun.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.papermodle.ExamSubject;
@Component("examutil")
public class Examutil
{
    private Logger log=LoggerFactory.getLogger(Examutil.class);
    @Autowired
    private RestTemplate restTemplate;
    public ExamSubject[] createPaper(String subjectType, Integer subjectDbId, Integer subjectCount)
    {
    	log.info("createPaper  subjectType "+subjectType+"subjectDbId "+subjectDbId+"subjectCount "+subjectCount);
        String url =
            "http://subject-manage/examPaperSubjects?subjectType={subjectType}&subjectDbId={subjectDbId}&subjectCount={subjectCount}";
        Map<String, Object> params = new HashMap<>();
        params.put("subjectType", subjectType);
        params.put("subjectDbId", subjectDbId);
        params.put("subjectCount", subjectCount);
        
        
        ExamSubject[] result=null;
        try
        {
            result =restTemplate.getForObject(url,ExamSubject[].class, params);
        }
        catch (RestClientException e)
        {
            log.error("get examsubject error"+e);
        }
              
        return result;
    }
    
    public ResponseMsg queryExamPass(Integer examPaperId,Integer examId,int score) {
    	String url="http://exam-manage/examispass?examPaperId={examPaperId}&examId={examId}&score={score}";
    	Map<String, Object> params = new HashMap<>();
        params.put("examPaperId", examPaperId);
        params.put("examId", examId);
        params.put("score", score);
        ResponseMsg msg=restTemplate.getForObject(url, ResponseMsg.class, params);
		return msg;
    }
}
