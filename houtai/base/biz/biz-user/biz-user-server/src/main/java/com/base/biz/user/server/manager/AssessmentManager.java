package com.base.biz.user.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.server.dao.AssessmentDao;
import com.base.biz.user.server.model.AssessmentDO;
import com.base.biz.user.server.model.AwardDO;
import com.base.biz.user.server.model.BizUserAddParam.AddParamAssessment;
import com.base.common.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/4/9 2:05 AM
 */
@Component
public class AssessmentManager {

    @Autowired
    private AssessmentDao assessmentDao;

    public void add(String userCode, List<AddParamAssessment> addParamAssessmentList) {
        if (CollectionUtils.isEmpty(addParamAssessmentList)) {
            return;
        }
        for (AddParamAssessment addParamAssessment : addParamAssessmentList) {
            AssessmentDO assessmentDO = new AssessmentDO();
            Date now = new Date();
            assessmentDO.setGmtCreate(now);
            assessmentDO.setGmtModified(now);
            assessmentDO.setUserCode(userCode);

            assessmentDO.setTime(DateUtil.convert2Date(addParamAssessment.time,BizUserConstant.DateFormat));
            assessmentDO.setGrade(addParamAssessment.grade);
            assessmentDO.setRemark(addParamAssessment.remark);
            assessmentDao.save(assessmentDO);
        }
    }
}
