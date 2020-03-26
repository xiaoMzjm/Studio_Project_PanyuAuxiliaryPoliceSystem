package com.base.biz.department.repository;

import com.base.biz.department.model.CampanyDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/27 2:03 AM
 */
@Repository
public interface CampanyDORespository extends JpaRepository<CampanyDO,Long> {
}
