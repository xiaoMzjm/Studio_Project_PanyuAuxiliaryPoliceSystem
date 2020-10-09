package com.base.biz.user.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.user.server.model.AssessmentDTO;
import com.base.biz.user.server.model.BizUserAddParam.AddParamAssessment;

/**
 * 考核
 * @author:小M
 * @date:2020/7/5 12:00 AM
 */
public interface AssessmentManager {

    void add(String userCode, List<AddParamAssessment> addParamAssessmentList);

    List<AssessmentDTO> listByUserCode(String userCode);

    List<AssessmentDTO> listByTime(Date timeStart, Date timeEnd);

    void deleteByUserCode(String userCode);
}
