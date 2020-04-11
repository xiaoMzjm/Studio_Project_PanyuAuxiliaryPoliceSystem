package com.base.biz.user.server.dao;

import com.base.biz.user.server.model.BizUserDO;
import com.base.biz.user.server.model.FamilyMemberDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/30 12:19 AM
 */
@Repository
public interface FamilyMemberDao extends JpaRepository<FamilyMemberDO,Long> {

    void deleteByUserCode(String userCode);
}
