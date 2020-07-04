package com.base.biz.user.server.manager;

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

    List<AssessmentDTO> findByUserCode(String userCode);

    void deleteByUserCode(String userCode);
}
