package com.base.authority.server.dao;

import com.base.authority.server.model.AuthorityDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/27 2:03 AM
 */
@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityDO,Long> {
}
