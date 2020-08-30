package com.base.biz.wages.server.dao;

import java.util.Date;
import java.util.List;

import com.base.biz.wages.server.model.WagesDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author:小M
 * @date:2020/8/30 11:56 AM
 */
@Repository
public interface BizWagesDAO extends JpaRepository<WagesDO,Long> {

    /**
     * 根据 身份证 查询
     * @param identityCardList
     * @return
     */
    List<WagesDO> findByIdentityCardIn(List<String> identityCardList);

    /**
     * 根据时间搜索
     * @param time
     * @return
     */
    List<WagesDO> findByTime(Date time);
}
