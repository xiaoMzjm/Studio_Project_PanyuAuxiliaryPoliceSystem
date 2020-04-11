package com.base.biz.user.server.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 个人履历表
 * @author:小M
 * @date:2020/4/5 1:46 PM
 */
public class PersonalExperienceConvertor {

    public static PersonalExperienceDTO do2dto(PersonalExperienceDO personalExperienceDO) {
        if (personalExperienceDO == null) {
            return null;
        }
        PersonalExperienceDTO personalExperienceDTO = new PersonalExperienceDTO();
        BeanUtils.copyProperties(personalExperienceDO, personalExperienceDTO);
        return personalExperienceDTO;
    }

    public static List<PersonalExperienceDTO> do2dtoList(List<PersonalExperienceDO> personalExperienceDOList) {
        if (CollectionUtils.isEmpty(personalExperienceDOList)) {
            return null;
        }
        List<PersonalExperienceDTO> result = Lists.newArrayList();
        for (PersonalExperienceDO p : personalExperienceDOList) {
            result.add(do2dto(p));
        }
        return result;
    }
}
