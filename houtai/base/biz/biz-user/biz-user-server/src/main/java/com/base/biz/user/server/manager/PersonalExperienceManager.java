package com.base.biz.user.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.server.dao.PersonalExperienceDao;
import com.base.biz.user.server.model.BizUserAddParam.AddParamExperience;
import com.base.biz.user.server.model.PersonalExperienceDO;
import com.base.common.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/4/9 1:17 AM
 */
@Component
public class PersonalExperienceManager {

    @Autowired
    private PersonalExperienceDao personalExperienceDao;

    public void add(String userCode, List<AddParamExperience> addParamExperienceList) {
        if (CollectionUtils.isEmpty(addParamExperienceList)) {
            return;
        }
        for (AddParamExperience addParamExperience : addParamExperienceList) {
            PersonalExperienceDO personalExperienceDO = new PersonalExperienceDO();
            Date now = new Date();
            personalExperienceDO.setGmtCreate(now);
            personalExperienceDO.setGmtModified(now);
            personalExperienceDO.setUserCode(userCode);
            personalExperienceDO.setTimeStart(DateUtil.convert2Date(addParamExperience.timeStart, BizUserConstant.DateFormat));
            personalExperienceDO.setTimeEnd(DateUtil.convert2Date(addParamExperience.timeEnd, BizUserConstant.DateFormat));
            personalExperienceDO.setUnit(addParamExperience.unit);
            personalExperienceDO.setCompany(addParamExperience.department);
            personalExperienceDO.setDuty(addParamExperience.duty);
            personalExperienceDao.save(personalExperienceDO);
        }
    }
}
