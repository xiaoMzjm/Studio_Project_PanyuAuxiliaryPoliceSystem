package com.base.biz.user.server.manager;

import java.util.Date;
import java.util.List;

import com.base.biz.user.client.common.BizUserConstant;
import com.base.biz.user.server.dao.FamilyMemberDao;
import com.base.biz.user.server.model.BizUserAddParam.AddParamFamilyMember;
import com.base.biz.user.server.model.FamilyMemberDO;
import com.base.biz.user.server.model.PersonalExperienceDO;
import com.base.common.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:小M
 * @date:2020/4/9 1:32 AM
 */
@Component
public class FamilyMemberManager {
    @Autowired
    private FamilyMemberDao familyMemberDao;

    public void add(String userCode, List<AddParamFamilyMember> addParamFamilyMemberList) {
        if (CollectionUtils.isEmpty(addParamFamilyMemberList)) {
            return;
        }
        for (AddParamFamilyMember addParamFamilyMember : addParamFamilyMemberList) {
            FamilyMemberDO familyMemberDO = new FamilyMemberDO();
            Date now = new Date();
            familyMemberDO.setGmtCreate(now);
            familyMemberDO.setGmtModified(now);
            familyMemberDO.setUserCode(userCode);

            familyMemberDO.setName(addParamFamilyMember.name);
            familyMemberDO.setRelation(addParamFamilyMember.relation);
            familyMemberDO.setCompany(addParamFamilyMember.company);
            familyMemberDO.setDuty(addParamFamilyMember.duty);
            familyMemberDO.setIdentityCard(addParamFamilyMember.identityCard);
            familyMemberDO.setPhone(addParamFamilyMember.phone);
            familyMemberDO.setPoliticalLandscapeCode(addParamFamilyMember.politicalLandscapeCode);
            familyMemberDao.save(familyMemberDO);
        }
    }
}
