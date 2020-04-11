package com.base.department.server.dao;

import java.util.List;

import com.base.department.server.model.CompanyDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/3/27 2:03 AM
 */
@Repository
public interface CompanyDORepository extends JpaRepository<CompanyDO,Long> {

    List<CompanyDO> findByCodeIn(List<String> codeList);
}
