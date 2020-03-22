package com.base.biz.web.api.example.example_jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2019/2/24 2:33 AM
 */
@Repository
public interface UserRepository extends JpaRepository<UserDO, Long> {
}
