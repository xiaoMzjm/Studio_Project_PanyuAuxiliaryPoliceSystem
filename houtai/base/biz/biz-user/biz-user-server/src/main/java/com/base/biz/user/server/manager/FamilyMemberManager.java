package com.base.biz.user.server.manager;

import java.util.List;

import com.base.biz.user.server.model.BizUserAddParam.AddParamFamilyMember;
import com.base.biz.user.server.model.FamilyMemberDTO;

/**
 * @author:小M
 * @date:2020/7/5 12:06 AM
 */
public interface FamilyMemberManager {

    void add(String userCode, List<AddParamFamilyMember> addParamFamilyMemberList);

    List<FamilyMemberDTO> findByUserCode(String userCode);

    void deleteByUserCode(String userCode);
}
