package com.base.biz.user.server.dao;

import com.base.biz.user.server.model.BizUserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/30 12:19 AM
 */
@Repository
public interface BizUserDao extends JpaRepository<BizUserDO,Long> {
}
