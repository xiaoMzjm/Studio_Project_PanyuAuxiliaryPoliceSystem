package com.base.biz.epidemic.server.dao;

import com.base.biz.epidemic.server.model.EpidemicDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/8/2 3:25 PM
 */
@Repository
public interface EpidemicDAO extends JpaRepository<EpidemicDO,Long> {

    EpidemicDO findByCode(String code);

    @Modifying
    @Query(nativeQuery = true, value = "update epidemic set status = 3 where status != 3")
    void commitAll();
}
