package com.hoperun.util;

import com.hoperun.commons.domain.ExamResponse;
import com.hoperun.commons.domain.Response;
import com.hoperun.commons.domain.ResultCode;
import com.hoperun.constant.PapersatusConstant;

public class ResopnseUtil {
    public static Response getResponse(String resultCode) {

        Response response = new Response();
        if (ResultCode.RESULTCODE_SUCESS.equals(resultCode)) {
            response.setResultDesc(ResultCode.RESULTCODE_SUCESS_DESC);
        } else {
            response.setResultDesc(ResultCode.RESULTCODE_FAIL_DESC);
        }
        response.setResultCode(resultCode);
        return response;

    }

    public static ExamResponse getExamResponse(String resultCode, int score, Integer isPass) {

        ExamResponse response = new ExamResponse();
      
            response.setResultDesc(PaperStatusMap.PAPERSTATUSMAP.get(isPass));
            response.setScore(score);
            response.setIsPass(isPass);
            response.setResultCode(resultCode);

        return response;

    }
}
