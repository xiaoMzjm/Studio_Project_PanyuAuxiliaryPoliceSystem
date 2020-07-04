package com.base.biz.user.server.manager.impl;

import java.util.Date;
import java.util.List;

import com.base.biz.user.server.dao.FamilyMemberDao;
import com.base.biz.user.server.manager.FamilyMemberManager;
import com.base.biz.user.server.model.BizUserAddParam.AddParamFamilyMember;
import com.base.biz.user.server.model.FamilyMemberConvertor;
import com.base.biz.user.server.model.FamilyMemberDO;
import com.base.biz.user.server.model.FamilyMemberDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

/**
 * 家庭成员
 * @author:小M
 * @date:2020/4/9 1:32 AM
 */
@Component
public class FamilyMemberManagerImpl implements FamilyMemberManager {
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
            familyMemberDO.setPoliticalLandscapeCode(addParamFamilyMember.politicalLandscape);
            familyMemberDao.save(familyMemberDO);
        }
    }

    /**
     * 根据userCode查询个人经历
     * @param userCode
     * @return
     */
    public List<FamilyMemberDTO> findByUserCode(String userCode) {
        FamilyMemberDO ddo = new FamilyMemberDO();
        ddo.setUserCode(userCode);
        Example<FamilyMemberDO> example = Example.of(ddo);
        List<FamilyMemberDO> list = familyMemberDao.findAll(example);
        return FamilyMemberConvertor.do2dtoList(list);
    }

    /**
     * 根据人员code删除
     * @param userCode
     */
    public void deleteByUserCode(String userCode) {
        familyMemberDao.deleteByUserCode(userCode);
    }
}
