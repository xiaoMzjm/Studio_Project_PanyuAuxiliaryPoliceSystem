package com.base.biz.expire.server.dao;

import java.util.Date;
import java.util.List;

import com.base.biz.expire.server.model.ExpireDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/5/24 2:59 PM
 */
@Repository
public interface ExpireDAO extends JpaRepository<ExpireDO,Long> {

    @Query(nativeQuery = true, value = "select * from expire where time >= :start and time < :end and type = :type order by gmt_create desc")
    List<ExpireDO> getByTime(@Param("start")Date start, @Param("end")Date end,@Param("type")Integer type);

    Long deleteByCode(String code);
}
