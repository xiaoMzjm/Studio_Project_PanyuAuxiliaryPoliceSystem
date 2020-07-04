package com.base.biz.user.server.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.server.dao.PersonalExperienceDao;
import com.base.biz.user.server.manager.PersonalExperienceManager;
import com.base.biz.user.server.model.BizUserAddParam.AddParamExperience;
import com.base.biz.user.server.model.PersonalExperienceConvertor;
import com.base.biz.user.server.model.PersonalExperienceDO;
import com.base.biz.user.server.model.PersonalExperienceDTO;
import com.base.common.util.DateUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * 个人经历
 * @author:小M
 * @date:2020/4/9 1:17 AM
 */
@Component
public class PersonalExperienceManagerImpl implements PersonalExperienceManager {

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

    /**
     * 根据userCode查询个人经历
     * @param userCode
     * @return
     */
    public List<PersonalExperienceDTO> findByUserCode(String userCode) {

        PersonalExperienceDO personalExperienceDO = new PersonalExperienceDO();
        personalExperienceDO.setUserCode(userCode);
        Example<PersonalExperienceDO> example = Example.of(personalExperienceDO);
        List<PersonalExperienceDO> list = personalExperienceDao.findAll(example);
        return PersonalExperienceConvertor.do2dtoList(list);
    }

    /**
     * 根据useCode删除
     * @param userCode
     */
    public void deleteByUserCode(String userCode) {
        personalExperienceDao.deleteByUserCode(userCode);
    }
}
