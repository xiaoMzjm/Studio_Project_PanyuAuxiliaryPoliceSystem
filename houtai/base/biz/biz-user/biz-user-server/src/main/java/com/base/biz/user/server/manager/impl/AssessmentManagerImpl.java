package com.base.biz.user.server.manager.impl;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.client.model.BizUserDetailVO.Assessment;
import com.base.biz.user.server.dao.AssessmentDao;
import com.base.biz.user.server.manager.AssessmentManager;
import com.base.biz.user.server.model.AssessmentConvertor;
import com.base.biz.user.server.model.AssessmentDO;
import com.base.biz.user.server.model.AssessmentDTO;
import com.base.biz.user.server.model.AwardDO;
import com.base.biz.user.server.model.BizUserAddParam.AddParamAssessment;
import com.base.biz.user.server.model.FamilyMemberConvertor;
import com.base.biz.user.server.model.FamilyMemberDO;
import com.base.biz.user.server.model.FamilyMemberDTO;
import com.base.common.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * 考核
 * @author:小M
 * @date:2020/4/9 2:05 AM
 */
@Component
public class AssessmentManagerImpl implements AssessmentManager {

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

    /**
     * 根据userCode查询个人经历
     * @param userCode
     * @return
     */
    public List<AssessmentDTO> findByUserCode(String userCode) {
        AssessmentDO ddo = new AssessmentDO();
        ddo.setUserCode(userCode);
        Example<AssessmentDO> example = Example.of(ddo);
        List<AssessmentDO> list = assessmentDao.findAll(example);
        return AssessmentConvertor.do2dtoList(list);
    }

    /**
     * 根据人员删除
     * @param userCode
     */
    public void deleteByUserCode(String userCode) {
        assessmentDao.deleteByUserCode(userCode);
    }
}
