package com.base.biz.authority.server.dao;

import com.base.biz.authority.server.model.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/7/30 1:07 AM
 */
@Repository
public interface RoleDao extends JpaRepository<RoleDO,Long> {
}
