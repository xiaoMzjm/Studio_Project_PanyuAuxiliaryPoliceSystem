package com.base.biz.user.server.manager;

import java.util.List;

import com.base.biz.user.server.model.BizUserAddParam.AddParamExperience;
import com.base.biz.user.server.model.PersonalExperienceDTO;

/**
 * @author:小M
 * @date:2020/7/5 12:06 AM
 */
public interface PersonalExperienceManager {

    void add(String userCode, List<AddParamExperience> addParamExperienceList);

    List<PersonalExperienceDTO> findByUserCode(String userCode);

    void deleteByUserCode(String userCode);
}
